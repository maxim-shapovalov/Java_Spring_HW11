package ru.gb.spring.my_timesheet.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gb.spring.my_timesheet.model.Project;
import ru.gb.spring.my_timesheet.model.Timesheet;
import ru.gb.spring.my_timesheet.service.ProjectService;

import java.util.List;

@RestController
@RequestMapping("/projects")
@Tag(name = "Projects", description = "API для работы с проектами")
public class ProjectController {
    private final ProjectService service;

    public ProjectController(ProjectService service) {
        this.service = service;
    }

    @Operation(summary = "Get Project By Id", description = "Получить проект по идентификатору")
    @API.NotFoundResponse
    @API.InternalErrorResponse
    @API.ProjectSuccessfulResponse
    @GetMapping("/{id}")
    public ResponseEntity<Project> get(@PathVariable @Parameter(description = "Идентификатор проекта") Long id){
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Get Timesheets By Project Id", description = "Получить все записи учёта времени по идентификатору проекта")
    @API.NotFoundResponse
    @API.InternalErrorResponse
    @API.ProjectSuccessfulResponse
    @GetMapping("/{id}/timesheets")
    public ResponseEntity<List<Timesheet>> getTimesheetsByProjectId(@PathVariable @Parameter(description = "Идентификатор проекта") Long id){
        if(service.findById(id).isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(service.getTimesheetsByProjectId(id));
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Get All Projects", description = "Получить все проекты")
    @API.NotFoundResponse
    @API.InternalErrorResponse
    @API.ProjectSuccessfulResponse
    @GetMapping
    public ResponseEntity<List<Project>> getAll(){
        return ResponseEntity.ok(service.findAll());
    }

    @Operation(summary = "Create New Project", description = "Создать новый проект")
    @API.NotFoundResponse
    @API.InternalErrorResponse
    @API.ProjectCreatedResponse
    @PostMapping
    public ResponseEntity<Project> create(@RequestBody Project project){
        project = service.create(project);
        return ResponseEntity.status(HttpStatus.CREATED).body(project);
    }

    @Operation(summary = "Delete Project By Id", description = "Удалить проект по идентификатору")
    @API.NotFoundResponse
    @API.InternalErrorResponse
    @API.ProjectSuccessfulResponse
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable @Parameter(description = "Идентификатор проекта") Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
