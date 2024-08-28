package ru.gb.spring.my_timesheet.generator;

import ru.gb.spring.my_timesheet.model.Project;
import ru.gb.spring.my_timesheet.repository.ProjectRepository;

public class ProjectGenerator {
    public void generateProjectsInRepository(ProjectRepository projectRepo, int num){
        for (int i = 1; i <= num; i++) {
            Project project = new Project();
            project.setName("Project #" + i);
            projectRepo.save(project);
        }
    }
}
