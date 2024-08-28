package ru.gb.spring.my_timesheet.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import ru.gb.spring.my_timesheet.model.Project;
import ru.gb.spring.my_timesheet.repository.ProjectRepository;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) // поднимит сервер, на который можно отправлять запросы
// RANDOM_PORT - случайный порт (его можно узнать ниже), DEFINED_PORT - порт, указанный в application-test.yml, MOCK - порта вообще не будет
//@AutoConfigureWebTestClient
class ProjectControllerTest {

    @Autowired
    ProjectRepository projectRepository;

//  @Autowired
//  WebTestClient webTestClient; // для этого класса требуется зависимость webflux. Класс очень похож на RestClient, очень похожие методы.
//  Единственное отличие, метод retrieve возвращает Mono и Flux

    @LocalServerPort // здесь Спринг заинжектит порт (случайный порт RANDOM_PORT), на котором развернут наш сервер
    private int port;
    private RestClient restClient;

    // select * from users where id = 5; // ждем 5 минут
    // select * from users; -> получаем данные порциями по 100 записей
    // Mono Flux

    @BeforeEach
    void beforeEach() {
        restClient = RestClient.create("http://localhost:" + port);
    }

    @Test
    void getByIdNotFound() {
//    try {
//      ///
//
//      fail();
//    } catch (HttpClientErrorException.BadRequest e) {
//       assertTrue(true);
//    }

        assertThrows(HttpClientErrorException.NotFound.class, () -> {
            restClient.get()
                    .uri("/projects/-2")
                    .retrieve()
                    .toBodilessEntity();
        });
    }

    @Test
    void getByIdAllOk() {
        // given
        Project project = new Project();
        project.setName("projectName");
        Project expected = projectRepository.save(project);

//    webTestClient.get()
//      .uri("/projects/" + project.getId())
//      .exchange() // retrieve
//      .expectStatus().isOk() //   assertEquals(HttpStatus.OK, actual.getStatusCode());
//      .expectBody(Project.class)
//      .value(actual -> {
//        assertEquals(expected.getId(), actual.getId());
//        assertEquals(expected.getName(), actual.getName());
//      });
        // GET /projects/{id}
        ResponseEntity<Project> actual = restClient.get()
                .uri("/projects/" + expected.getId())
                .retrieve()
                .toEntity(Project.class);

        // assert 200 OK
        assertEquals(HttpStatus.OK, actual.getStatusCode());
        Project responseBody = actual.getBody();
        assertNotNull(responseBody);
        assertEquals(project.getId(), responseBody.getId());
        assertEquals(project.getName(), responseBody.getName());
    }

    @Test
    void testCreate() {
        // POST /projects
        Project toCreate = new Project();
        toCreate.setName("NewName");

        ResponseEntity<Project> response = restClient.post()
                .uri("/projects")
                .body(toCreate)
                .retrieve()
                .toEntity(Project.class);

        // Проверяем HTTP-ручку сервера
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Project responseBody = response.getBody();
        assertNotNull(responseBody);
        assertNotNull(responseBody.getId());
        assertEquals(responseBody.getName(), toCreate.getName());

        // Проверяем, что запись в БД есть
        assertTrue(projectRepository.existsById(responseBody.getId()));
    }

    @Test
    void testDeleteById() {
        Project toDelete = new Project();
        toDelete.setName("NewName");
        toDelete = projectRepository.save(toDelete);

        ResponseEntity<Void> response = restClient.delete()
                .uri("/projects/" + toDelete.getId())
                .retrieve()
                .toBodilessEntity(); // less
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

        // Проверяем, что запись в БД НЕТ
        assertFalse(projectRepository.existsById(toDelete.getId()));
    }

}