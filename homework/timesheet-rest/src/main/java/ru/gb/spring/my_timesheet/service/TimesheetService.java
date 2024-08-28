package ru.gb.spring.my_timesheet.service;

import org.slf4j.event.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.gb.spring.aspect.recover.Recover;
import ru.gb.spring.aspect.timer.Timer;
import ru.gb.spring.my_timesheet.model.Project;
import ru.gb.spring.my_timesheet.model.Timesheet;
import ru.gb.spring.my_timesheet.repository.ProjectRepository;
import ru.gb.spring.my_timesheet.repository.TimesheetRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

@Service
@Timer(level = Level.TRACE)
public class TimesheetService {

    private final TimesheetRepository timesheetRepository;
    private final ProjectRepository projectRepository;

    public TimesheetService(TimesheetService timesheetService) {
        this(timesheetService.timesheetRepository, timesheetService.projectRepository);
    }
    @Autowired
    public TimesheetService(TimesheetRepository timesheetRepository, ProjectRepository projectRepository) {
        this.timesheetRepository = timesheetRepository;
        this.projectRepository = projectRepository;
    }

//    @Timer
    public Optional<Timesheet> findById(Long id) {
        return timesheetRepository.findById(id);
    }

    public List<Timesheet> findAll() {
        return timesheetRepository.findAll();
    }

    public List<Timesheet> findByCreatedAfter(LocalDate date){
        return timesheetRepository.findByCreatedAtAfter(date);
//        return timesheets.stream().filter(elem -> (elem.getCreatedAt().compareTo(date.atStartOfDay()) > 0 )).toList();
    }

    public List<Timesheet> findByCreatedBefore(LocalDate date){
        return timesheetRepository.findByCreatedAtBefore(date);
//        return timesheets.stream().filter(elem -> (elem.getCreatedAt().compareTo(date.atStartOfDay()) < 0 )).toList();
    }

    public Timesheet create(Timesheet timesheet) {
        if (Objects.isNull(timesheet.getProject().getId())) {
            throw new IllegalArgumentException("projectId must not be null");
        }

        if (projectRepository.findById(timesheet.getProject().getId()).isEmpty()) {
            throw new NoSuchElementException("Project with id " + timesheet.getProject().getId() + " does not exists");
        }

        timesheet.setCreatedAt(LocalDate.now());
        return timesheetRepository.save(timesheet);
    }

    public void delete(Long id) {
        timesheetRepository.deleteById(id);
    }

    @Recover(level = Level.TRACE)
    public Optional<Project> getProjectById(Long id){
        if (projectRepository.findById(id).isEmpty()) {
            throw new NoSuchElementException("Employee with id = " + id + " does not exists");
        }
        return projectRepository.findById(id);
    }

    @Recover(level = Level.TRACE)
    public Timesheet update(Long id, Timesheet timesheet) {
        Timesheet existingTimesheet = timesheetRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Timesheet with id " + id + " does not exist"));

        existingTimesheet.setProject(timesheet.getProject());
        existingTimesheet.setEmployee(timesheet.getEmployee());
        existingTimesheet.setMinutes(timesheet.getMinutes());
        existingTimesheet.setCreatedAt(timesheet.getCreatedAt());

        return timesheetRepository.save(existingTimesheet);
    }

}