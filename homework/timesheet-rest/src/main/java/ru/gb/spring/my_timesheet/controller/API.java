package ru.gb.spring.my_timesheet.controller;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import ru.gb.spring.my_timesheet.model.Employee;
import ru.gb.spring.my_timesheet.model.Project;
import ru.gb.spring.my_timesheet.model.Timesheet;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public final class API {

    @Retention(RetentionPolicy.RUNTIME)
    @ApiResponse(description = "Данные не найдены", responseCode = "404", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    public @interface NotFoundResponse {

    }

    @Retention(RetentionPolicy.RUNTIME)
    @ApiResponse(description = "Внутренняя ошибка", responseCode = "500", content = @Content(schema = @Schema(implementation = Void.class)))
    public @interface InternalErrorResponse{

    }

    @Retention(RetentionPolicy.RUNTIME)
    @ApiResponse(description = "Успешный ответ", responseCode = "200", content = @Content(schema = @Schema(implementation = Project.class)))
    public @interface ProjectSuccessfulResponse{

    }

    @Retention(RetentionPolicy.RUNTIME)
    @ApiResponse(description = "Успешный ответ", responseCode = "200", content = @Content(schema = @Schema(implementation = Employee.class)))
    public @interface EmployeeSuccessfulResponse{

    }

    @Retention(RetentionPolicy.RUNTIME)
    @ApiResponse(description = "Успешный ответ", responseCode = "200", content = @Content(schema = @Schema(implementation = Timesheet.class)))
    public @interface TimesheetSuccessfulResponse{

    }

    @Retention(RetentionPolicy.RUNTIME)
    @ApiResponse(description = "Проект успешно создан", responseCode = "201", content = @Content(schema = @Schema(implementation = Project.class)))
    public @interface ProjectCreatedResponse{

    }

    @Retention(RetentionPolicy.RUNTIME)
    @ApiResponse(description = "Сотрудник успешно создан", responseCode = "201", content = @Content(schema = @Schema(implementation = Employee.class)))
    public @interface EmployeeCreatedResponse{

    }

    @Retention(RetentionPolicy.RUNTIME)
    @ApiResponse(description = "Запись учёта времени успешно создана", responseCode = "201", content = @Content(schema = @Schema(implementation = Timesheet.class)))
    public @interface TimesheetCreatedResponse{

    }

    @Retention(RetentionPolicy.RUNTIME)
    @ApiResponse(description = "Некорректный запрос", responseCode = "400", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    public @interface TimesheetBadRequestResponse{

    }


}
