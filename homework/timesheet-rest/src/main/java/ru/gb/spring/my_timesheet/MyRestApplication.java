package ru.gb.spring.my_timesheet;

import org.slf4j.event.Level;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ApplicationContext;
import ru.gb.spring.aspect.recover.Recover;
import ru.gb.spring.my_timesheet.generator.EmployeeGenerator;
import ru.gb.spring.my_timesheet.generator.ProjectGenerator;
import ru.gb.spring.my_timesheet.generator.TimesheetGenerator;
import ru.gb.spring.my_timesheet.model.Role;
import ru.gb.spring.my_timesheet.model.RoleName;
import ru.gb.spring.my_timesheet.model.User;
import ru.gb.spring.my_timesheet.model.UserRole;
import ru.gb.spring.my_timesheet.repository.*;
import ru.gb.spring.my_timesheet.service.TimesheetService;

@EnableDiscoveryClient
@SpringBootApplication
//@Import(LoggingAutoConfiguration.class) // вместо этого в logging-starter ресурсах создан META-INF/spring/file с путем к этому классу
public class MyRestApplication {
	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(MyRestApplication.class, args);

		RoleRepository roleRepository = ctx.getBean(RoleRepository.class);
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

		UserRepository userRepository = ctx.getBean(UserRepository.class);

		User admin = new User();
		admin.setLogin("admin");
		admin.setPassword("$2a$12$LbAPCsHn8ZN5MUDqDmIX7e9n1YlDkCxEt0lW3Q2WuW0M1vteo8jvG"); // admin - (bcrypt-generator.com) Хэш от admin - то,
		// что будет храниться в БД. При вводе верного пароля (admin), он будет преобразован в хэш, который должен соответствовать тому, что сохранен.

		User user = new User();
		user.setLogin("user");
		user.setPassword("$2a$12$zRccUkXS1abZxoL6MYRHx.wJENZgeLJSkrIQYYxEAVj8jqPC.wgdW"); // user - Хэш от user

		User anonymous = new User();
		anonymous.setLogin("anon");
		anonymous.setPassword("$2a$12$tPkyYzWCYUEePUFXUh3scesGuPum1fvFYwm/9UpmWNa52xEeUToRu"); // anon - Хэш от anon

		User rest = new User();
		rest.setLogin("rest");
		rest.setPassword("$2a$12$/8Xwei8n8/PkBtxotUzDPuhP/ODGwY2aCJLIcIpiR0z/qQhLfZNMK");

		admin = userRepository.save(admin);
		user = userRepository.save(user);
		anonymous = userRepository.save(anonymous);
		rest = userRepository.save(rest);

		UserRoleRepository userRoleRepository = ctx.getBean(UserRoleRepository.class);
		// id user_id role_name
		//  1       1     admin
		//  2       1     user
		//  3       1     rest
		//  4       2     user
		//  5       2     rest
		//  6       3     rest
		UserRole adminAdminRole = new UserRole();
		adminAdminRole.setUser(admin);
		adminAdminRole.setRole(roleRepository.findByName(RoleName.ADMIN));
		userRoleRepository.save(adminAdminRole);

		UserRole adminUserRole = new UserRole();
		adminUserRole.setUser(admin);
		adminUserRole.setRole(roleRepository.findByName(RoleName.USER));
		userRoleRepository.save(adminUserRole);

		UserRole adminRestRole = new UserRole();
		adminRestRole.setUser(admin);
		adminRestRole.setRole(roleRepository.findByName(RoleName.REST));
		userRoleRepository.save(adminRestRole);

		UserRole userUserRole = new UserRole();
		userUserRole.setUser(user);
		userUserRole.setRole(roleRepository.findByName(RoleName.USER));
		userRoleRepository.save(userUserRole);

		UserRole userRestRole = new UserRole();
		userRestRole.setUser(user);
		userRestRole.setRole(roleRepository.findByName(RoleName.REST));
		userRoleRepository.save(userRestRole);

		UserRole restRestRole = new UserRole();
		restRestRole.setUser(rest);
		restRestRole.setRole(roleRepository.findByName(RoleName.REST));
		userRoleRepository.save(restRestRole);

//		RoleRepository roleRepository = ctx.getBean(RoleRepository.class);
//		RoleGenerator roleGenerator = new RoleGenerator();
//		roleGenerator.generateRolesInRepository(roleRepository);
//
//		UserRepository userRepository = ctx.getBean(UserRepository.class);
//		UserGenerator userGenerator = new UserGenerator();
//		userGenerator.generateUsersInRepository(userRepository);
//
//		UserRoleRepository userRoleRepository = ctx.getBean(UserRoleRepository.class);
//		UserRoleGenerator userRoleGenerator = new UserRoleGenerator();
//		userRoleGenerator.generateUserRolesInRepository(userRoleRepository, roleRepository, userGenerator);

		ProjectRepository projectRepo = ctx.getBean(ProjectRepository.class);
		ProjectGenerator projectGenerator = new ProjectGenerator();
		projectGenerator.generateProjectsInRepository(projectRepo, 5);

		EmployeeRepository employeeRepo = ctx.getBean(EmployeeRepository.class);
		EmployeeGenerator employeeGenerator = new EmployeeGenerator();
		employeeGenerator.generateEmployeesInRepository(employeeRepo, 5);

		TimesheetRepository timesheetRepo = ctx.getBean(TimesheetRepository.class);
		TimesheetGenerator timesheetGenerator = new TimesheetGenerator();
		timesheetGenerator.generateTimesheetsInRepository(timesheetRepo, employeeRepo, projectRepo, 10);

		System.out.println("__________________________________________________________________________________");
		System.out.println("__________________________________________________________________________________");
		TimesheetService timesheetService = new TimesheetService(timesheetRepo, projectRepo);
		System.out.println("Result of Recover annotation: " + timesheetService.getProjectById(100L));
	}
}