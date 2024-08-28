package ru.gb.spring.my_timesheet.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.gb.spring.my_timesheet.model.Employee;
import ru.gb.spring.my_timesheet.model.Project;
import ru.gb.spring.my_timesheet.model.Timesheet;
import ru.gb.spring.my_timesheet.repository.EmployeeRepository;
import ru.gb.spring.my_timesheet.repository.ProjectRepository;
import ru.gb.spring.my_timesheet.repository.TimesheetRepository;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
public class TimesheetServiceTest {

    @Autowired
    TimesheetService timesheetService;

    @Test
    void findProjectByIdPresent() {

        Exception exception = assertThrows(Exception.class, () -> {timesheetService.getProjectById(100L);});

        String expectedMessage = "Employee with id = 100 does not exists";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }
}

