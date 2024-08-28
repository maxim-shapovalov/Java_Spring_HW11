package ru.gb.spring.my_timesheet.generator;

import ru.gb.spring.my_timesheet.model.Timesheet;
import ru.gb.spring.my_timesheet.repository.EmployeeRepository;
import ru.gb.spring.my_timesheet.repository.ProjectRepository;
import ru.gb.spring.my_timesheet.repository.TimesheetRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.concurrent.ThreadLocalRandom;

public class TimesheetGenerator {
    public void generateTimesheetsInRepository(TimesheetRepository timesheetRepo, EmployeeRepository employeeRepo, ProjectRepository projectRepo, int num){
        LocalDateTime createdAt = LocalDateTime.now();
        for (int i = 1; i <= num; i++) {
            createdAt = createdAt.plusDays(1).plusHours(1);

            Timesheet timesheet = new Timesheet();
            timesheet.setProject(projectRepo.getReferenceById(ThreadLocalRandom.current().nextLong(1, 6)));
            timesheet.setEmployee(employeeRepo.getReferenceById(ThreadLocalRandom.current().nextLong(1, 6)));
            timesheet.setCreatedAt(LocalDate.from(createdAt));
            timesheet.setMinutes(ThreadLocalRandom.current().nextInt(100, 1000));

            timesheetRepo.save(timesheet);
        }
    }
}
