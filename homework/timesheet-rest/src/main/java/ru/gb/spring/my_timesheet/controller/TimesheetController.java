package ru.gb.spring.my_timesheet.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gb.spring.my_timesheet.model.Timesheet;
import ru.gb.spring.my_timesheet.service.TimesheetService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/timesheets")
@Tag(name = "Timesheets", description = "API для работы с записями учёта времени")
public class TimesheetController {
    private final TimesheetService service;

    public TimesheetController(TimesheetService service) {
        this.service = service;
    }

    @Operation(summary = "Get Timesheet By Id", description = "Получить запись учёта времени по идентификатору")
    @API.NotFoundResponse
    @API.InternalErrorResponse
    @API.TimesheetSuccessfulResponse
    @GetMapping("/{id}")
    public ResponseEntity<Timesheet> get(@PathVariable @Parameter(description = "Идентификатор записи учёта времени") Long id) {
        Optional<Timesheet> ts = service.findById(id);
        if(ts.isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(ts.get());
        }

        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Get All Timesheets", description = "Получить все записи учёта времени")
    @API.NotFoundResponse
    @API.InternalErrorResponse
    @API.TimesheetSuccessfulResponse
    @GetMapping()
    public ResponseEntity<List<Timesheet>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @Operation(summary = "Get Timesheets Created After Specified Date", description = "Получить все записи учёта времени, созданные после указанной даты")
    @API.NotFoundResponse
    @API.InternalErrorResponse
    @API.TimesheetSuccessfulResponse
    @GetMapping("/after")
    public ResponseEntity<List<Timesheet>> getCreatedAfter(@RequestParam @Parameter(description = "Созданные после указанной даты") LocalDate createdAtAfter){
        return ResponseEntity.status(HttpStatus.OK).body(service.findByCreatedAfter(createdAtAfter));
    }

    @Operation(summary = "Get Timesheets Created Before Specified Date", description = "Получить все записи учёта времени, созданные до указанной даты")
    @API.NotFoundResponse
    @API.InternalErrorResponse
    @API.TimesheetSuccessfulResponse
    @GetMapping("/before")
    public ResponseEntity<List<Timesheet>> getCreatedBefore(@RequestParam @Parameter(description = "Созданные до указанной даты") LocalDate createdAtBefore){
        return ResponseEntity.status(HttpStatus.OK).body(service.findByCreatedBefore(createdAtBefore));
    }

    @Operation(summary = "Create New Timesheet", description = "Создать новую запись учёта времени")
    @API.NotFoundResponse
    @API.InternalErrorResponse
    @API.TimesheetCreatedResponse
    @API.TimesheetBadRequestResponse
    @PostMapping
    public ResponseEntity<Timesheet> create(@RequestBody Timesheet timesheet) {
        if(service.getProjectById(timesheet.getProject().getId()).isPresent()){
            timesheet = service.create(timesheet);
            return ResponseEntity.status(HttpStatus.CREATED).body(timesheet);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(timesheet);
    }

    @Operation(summary = "Delete Timesheet By Id", description = "Удалить запись учёта времени по идентификатору")
    @API.NotFoundResponse
    @API.InternalErrorResponse
    @API.TimesheetSuccessfulResponse
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable @Parameter(description = "Идентификатор записи учёта времени") Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Update Timesheet By Id", description = "Измениить запись учёта времени по идентификатору")
    @PutMapping("/{id}")
    @API.NotFoundResponse
    @API.InternalErrorResponse
    @API.TimesheetSuccessfulResponse
    public ResponseEntity<Timesheet> update(@PathVariable @Parameter(description = "Идентификатор записи учёта времени") Long id, @RequestBody Timesheet timesheet) {
        Timesheet updatedTimesheet = service.update(id, timesheet);
        return ResponseEntity.ok(updatedTimesheet);
    }
}
