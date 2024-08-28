//package ru.gb.spring.my_timesheet.service;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import ru.gb.spring.my_timesheet.model.Project;
//import ru.gb.spring.my_timesheet.page.ProjectPageDto;
//
//import java.util.List;
//import java.util.Optional;
//
//@Service
//@RequiredArgsConstructor
//public class ProjectPageService {
//    private final TimesheetService timesheetService;
//    private final ProjectService projectService;
//    public List<ProjectPageDto> findAll() {
//        return projectService.findAll().stream().map(this::convert).toList();
//    }
//
//    public Optional<ProjectPageDto> findById(Long id) {
//        return projectService.findById(id).map(this::convert);
//    }
//
//    public ProjectPageDto convert(Project project){
//        ProjectPageDto projectPageParameters = new ProjectPageDto();
//        projectPageParameters.setId(String.valueOf(project.getId()));
//        projectPageParameters.setName(project.getName());
//
////        projectPageParameters.setTimesheets(timesheetRepository.getTimesheetsByProjectId(project.getId()));
//
//        return projectPageParameters;
//    }
//}
