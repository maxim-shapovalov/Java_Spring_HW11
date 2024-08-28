package ru.gb.spring.my_timesheet.generator;

import ru.gb.spring.my_timesheet.model.Role;
import ru.gb.spring.my_timesheet.model.RoleName;
import ru.gb.spring.my_timesheet.repository.RoleRepository;

public class RoleGenerator {
    public void generateRolesInRepository(RoleRepository roleRepository){
        Role roleAdmin = new Role();
        roleAdmin.setName(RoleName.ADMIN);
        roleRepository.save(roleAdmin);

        Role roleUser = new Role();
        roleUser.setName(RoleName.USER);
        roleRepository.save(roleUser);

        Role roleAnonymous = new Role();
        roleAnonymous.setName(RoleName.ANONYMOUS);
        roleRepository.save(roleAnonymous);

        Role roleRest = new Role();
        roleRest.setName(RoleName.REST);
        roleRepository.save(roleRest);
    }
}
