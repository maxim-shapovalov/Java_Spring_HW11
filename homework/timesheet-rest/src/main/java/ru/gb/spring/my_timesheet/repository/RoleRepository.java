package ru.gb.spring.my_timesheet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gb.spring.my_timesheet.model.Role;
import ru.gb.spring.my_timesheet.model.RoleName;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(RoleName name);
}
