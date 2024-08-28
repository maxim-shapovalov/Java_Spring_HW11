package ru.gb.spring.my_timesheet.generator;

import ru.gb.spring.my_timesheet.model.RoleName;
import ru.gb.spring.my_timesheet.model.UserRole;
import ru.gb.spring.my_timesheet.repository.RoleRepository;
import ru.gb.spring.my_timesheet.repository.UserRoleRepository;

public class UserRoleGenerator {
    // id user_id role_name
    //  1       1     admin
    //  2       1     user
    //  3       1     rest
    //  4       2     user
    //  5       2     rest
    //  6       3     rest
    public void generateUserRolesInRepository(UserRoleRepository userRoleRepository, RoleRepository roleRepository, UserGenerator userGenerator){
        UserRole adminAdminRole = new UserRole();
        adminAdminRole.setUser(userGenerator.getAdmin());
        adminAdminRole.setRole(roleRepository.findByName(RoleName.ADMIN));
        userRoleRepository.save(adminAdminRole);

        UserRole adminUserRole = new UserRole();
        adminUserRole.setUser(userGenerator.getAdmin());
        adminUserRole.setRole(roleRepository.findByName(RoleName.USER));
        userRoleRepository.save(adminUserRole);

        UserRole adminRestRole = new UserRole();
        adminRestRole.setUser(userGenerator.getAdmin());
        adminRestRole.setRole(roleRepository.findByName(RoleName.REST));
        userRoleRepository.save(adminRestRole);

        UserRole userUserRole = new UserRole();
        userUserRole.setUser(userGenerator.getUser());
        userUserRole.setRole(roleRepository.findByName(RoleName.USER));
        userRoleRepository.save(userUserRole);

        UserRole userRestRole = new UserRole();
        userRestRole.setUser(userGenerator.getUser());
        userRestRole.setRole(roleRepository.findByName(RoleName.REST));
        userRoleRepository.save(userRestRole);

        UserRole restRestRole = new UserRole();
        restRestRole.setUser(userGenerator.getRest());
        restRestRole.setRole(roleRepository.findByName(RoleName.REST));
        userRoleRepository.save(restRestRole);
    }
}
