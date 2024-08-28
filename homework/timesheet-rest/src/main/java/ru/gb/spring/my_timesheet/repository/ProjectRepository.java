package ru.gb.spring.my_timesheet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gb.spring.my_timesheet.model.Project;

//@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
}
