package ru.gb.spring.my_timesheet.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import ru.gb.spring.my_timesheet.model.Employee;
import ru.gb.spring.my_timesheet.model.Project;
import ru.gb.spring.my_timesheet.model.Timesheet;
import ru.gb.spring.my_timesheet.repository.EmployeeRepository;
import ru.gb.spring.my_timesheet.repository.ProjectRepository;
import ru.gb.spring.my_timesheet.repository.TimesheetRepository;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TimesheetControllerTest {
    @Autowired
    TimesheetRepository timesheetRepository;
    @Autowired
    ProjectRepository projectRepository;
    @Autowired
    EmployeeRepository employeeRepository;
    @LocalServerPort
    private int port;
    private RestClient restClient;

    @BeforeEach
    void beforeEach() {
        restClient = RestClient.create("http://localhost:" + port);
        timesheetRepository.deleteAll();
    }

    @Test
    void testGetByIdNotFound() {
        assertThrows(HttpClientErrorException.NotFound.class, () -> {
            restClient.get()
                    .uri("/timesheets/-2")
                    .retrieve()
                    .toBodilessEntity();
        });
    }

    @Test
    void testGetByIdAllOk() {
        Timesheet timesheet = createTimesheet(1);
        Timesheet expected = timesheetRepository.save(timesheet);

        ResponseEntity<Timesheet> actual = restClient.get()
                .uri("/timesheets/" + expected.getId())
                .retrieve()
                .toEntity(Timesheet.class);

        assertEquals(HttpStatus.OK, actual.getStatusCode());
        Timesheet responseBody = actual.getBody();
        assertNotNull(responseBody);
        assertEquals(expected.getId(), responseBody.getId());
        assertEquals(expected.getProject(), responseBody.getProject());
        assertEquals(expected.getEmployee(), responseBody.getEmployee());
        assertEquals(expected.getMinutes(), responseBody.getMinutes());
        assertEquals(expected.getCreatedAt(), responseBody.getCreatedAt());
}

    @Test
    void testGetAll() {
        List<Timesheet> timesheetList = List.of(createTimesheet(1), createTimesheet(2), createTimesheet(3));
        for (Timesheet timesheet : timesheetList) {
            timesheetRepository.save(timesheet);
        }

        ResponseEntity<List<Timesheet>> actual = restClient.get()
                .uri("/timesheets")
                .retrieve()
                .toEntity(new ParameterizedTypeReference<List<Timesheet>>() {
                });

        assertEquals(HttpStatus.OK, actual.getStatusCode());
        List<Timesheet> responseBody = actual.getBody();
        assertNotNull(responseBody);
        assertEquals(timesheetList.size(), responseBody.size());
        assertArrayEquals(timesheetList.toArray(), responseBody.toArray());
//        for (int i = 0; i < timesheetList.size(); i++) {
//            assertEquals(timesheetList.get(i), responseBody.get(i));
//        }
    }

    @Test
    void testCreate() {
        Timesheet timesheet = createTimesheet(1);

        ResponseEntity<Timesheet> response = restClient.post()
                .uri("/timesheets")
                .body(timesheet)
                .retrieve()
                .toEntity(Timesheet.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Timesheet responseBody = response.getBody();
        assertNotNull(responseBody);
        assertNotNull(responseBody.getId());
        assertEquals(responseBody.getProject(), timesheet.getProject());
        assertEquals(responseBody.getEmployee(), timesheet.getEmployee());
        assertEquals(responseBody.getMinutes(), timesheet.getMinutes());
        assertEquals(responseBody.getCreatedAt(), timesheet.getCreatedAt());

        assertTrue(timesheetRepository.existsById(responseBody.getId()));
    }

    @Test
    void testDeleteById() {
        Timesheet toDelete = timesheetRepository.save(createTimesheet(1));

        ResponseEntity<Void> response = restClient.delete()
                .uri("/timesheets/" + toDelete.getId())
                .retrieve()
                .toBodilessEntity(); // less
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

        assertFalse(timesheetRepository.existsById(toDelete.getId()));
    }

    @Test
    void testUpdateById() {
        Timesheet timesheetToUpdate = timesheetRepository.save(createTimesheet(1));
        assertTrue(timesheetRepository.existsById(timesheetToUpdate.getId()));

        Timesheet expected = createTimesheet(100);

        ResponseEntity<Timesheet> response = restClient.put()
                .uri("/timesheets/" + timesheetToUpdate.getId())
                .body(expected)
                .retrieve()
                .toEntity(Timesheet.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        Timesheet responseBody = response.getBody();
        assertNotNull(responseBody);
        assertNotNull(responseBody.getId());
        assertEquals(responseBody.getProject(), expected.getProject());
        assertEquals(responseBody.getEmployee(), expected.getEmployee());
        assertEquals(responseBody.getMinutes(), expected.getMinutes());
        assertEquals(responseBody.getCreatedAt(), expected.getCreatedAt());
    }

    Timesheet createTimesheet(int num){
        Project project = new Project();
        project.setName("projectName" + num);
        project = projectRepository.save(project);

        Employee employee = new Employee();
        employee.setFirstName("firstName" + num);
        employee.setLastName("lastName" + num);
        employee.setPhone("1234" + num);
        employee.setDepartment("departmentName" + num);
        employee = employeeRepository.save(employee);

        Timesheet timesheet = new Timesheet();
        timesheet.setProject(project);
        timesheet.setEmployee(employee);
        timesheet.setMinutes(200 + num);
        timesheet.setCreatedAt(LocalDate.now());

        return timesheet;
    }
}