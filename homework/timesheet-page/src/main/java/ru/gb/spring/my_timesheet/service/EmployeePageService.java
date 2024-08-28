//package ru.gb.spring.my_timesheet.service;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import ru.gb.spring.my_timesheet.model.Employee;
//import ru.gb.spring.my_timesheet.page.EmployeePageDto;
//
//import java.util.List;
//import java.util.Optional;
//
//@Service
//@RequiredArgsConstructor
//public class EmployeePageService {
//    private final EmployeeService employeeService;
//
//    public List<EmployeePageDto> findAll() {
//        return employeeService.findAll().stream()
//                .map(this::convert)
//                .toList();
//    }
//
//    public Optional<EmployeePageDto> findById(Long id) {
//        return employeeService.findById(id)
//                .map(this::convert);
//    }
//
//    private EmployeePageDto convert(Employee employee) {
//
//        EmployeePageDto employeePageParameters = new EmployeePageDto();
//        employeePageParameters.setId(String.valueOf(employee.getId()));
//        employeePageParameters.setFirstName(employee.getFirstName());
//        employeePageParameters.setLastName(employee.getLastName());
//        employeePageParameters.setPhone(employee.getPhone());
//        employeePageParameters.setDepartment(employee.getDepartment());
//        employeePageParameters.setTimesheetList(employeeService.getTimesheetsByEmployeeId(employee.getId()));
//
//        return employeePageParameters;
//    }
//}
