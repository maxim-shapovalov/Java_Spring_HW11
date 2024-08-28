package ru.gb.spring.my_timesheet.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gb.spring.my_timesheet.model.Employee;
import ru.gb.spring.my_timesheet.model.Timesheet;
import ru.gb.spring.my_timesheet.service.EmployeeService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/employees")
@Tag(name = "Employees", description = "API для работы с сотрудниками")
public class EmployeeController {
    private final EmployeeService service;


    public EmployeeController(EmployeeService service) {
        this.service = service;
    }

    @Operation(summary = "Get Employee By Id", description = "Получить сотрудника по идентификатору")
    @API.NotFoundResponse
    @API.InternalErrorResponse
    @API.EmployeeSuccessfulResponse
    @GetMapping("/{id}")
    public ResponseEntity<Employee> get(@PathVariable @Parameter(description = "Идентификатор сотрудника") Long id){
        Optional<Employee> employee = service.findById(id);
        if(employee.isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(employee.get());
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Get Timesheets By Employee Id", description = "Получить все записи учёта времени по идентификатору сотрудника")
    @API.NotFoundResponse
    @API.InternalErrorResponse
    @API.EmployeeSuccessfulResponse
    @GetMapping("/{id}/timesheets")
    public ResponseEntity<List<Timesheet>> getTimesheetsByEmployeeId(@PathVariable @Parameter(description = "Идентификатор сотрудника") Long id){
        if(service.findById(id).isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(service.getTimesheetsByEmployeeId(id));
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Get All Employees", description = "Получить всех сотрудников")
    @API.NotFoundResponse
    @API.InternalErrorResponse
    @API.EmployeeSuccessfulResponse
    @GetMapping
    public ResponseEntity<List<Employee>> getAll(){
        return ResponseEntity.ok(service.findAll());
    }

    @Operation(summary = "Create New Employee", description = "Создать нового сотрудника")
    @API.NotFoundResponse
    @API.InternalErrorResponse
    @API.EmployeeCreatedResponse
    @PostMapping
    public ResponseEntity<Employee> create(@RequestBody Employee employee){
        employee = service.create(employee);
        return ResponseEntity.status(HttpStatus.CREATED).body(employee);
    }

    @Operation(summary = "Delete Employee By Id", description = "Удалить сотрудника по идентификатору")
    @API.NotFoundResponse
    @API.InternalErrorResponse
    @API.EmployeeSuccessfulResponse
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable @Parameter(description = "Идентификатор сотрудника") Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
