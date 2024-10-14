package com.example.testserverapp.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.testserverapp.entity.Employee;
import com.example.testserverapp.model.request.EmployeeRequest;
import com.example.testserverapp.model.response.EmployeeResponse;
import com.example.testserverapp.model.response.UserResponse;
import com.example.testserverapp.service.EmployeeService;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    // Get all employees
    @GetMapping
    public List<EmployeeResponse> getAllEmployees() {
        return employeeService.getAllEmployees().stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    // Get employee by ID
    @GetMapping("/id")
    public ResponseEntity<EmployeeResponse> getEmployeeById(@PathVariable Long id) {
        Employee employee = employeeService.getEmployeeById(id);
        EmployeeResponse employeeResponse = mapToResponse(employee);
        return ResponseEntity.ok(employeeResponse);
    }

    // Create new employee
    @PostMapping
    public ResponseEntity<EmployeeResponse> createEmployee(@RequestBody EmployeeRequest employeeRequest) {
        Employee employee = mapToEntity(employeeRequest);
        Employee createdEmployee = employeeService.createEmployee(employee);
        return new ResponseEntity<>(mapToResponse(createdEmployee), HttpStatus.CREATED);
    }

    // Update employee by ID
    @PutMapping("/id")
    public ResponseEntity<EmployeeResponse> updateEmployee(@PathVariable Long id, @RequestBody EmployeeRequest employeeRequest) {
        Employee employeeDetails = mapToEntity(employeeRequest);
        Employee updatedEmployee = employeeService.updateEmployee(id, employeeDetails);
        return ResponseEntity.ok(mapToResponse(updatedEmployee));
    }

    // Delete employee by ID
    @DeleteMapping("/id")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Mapping methods
    private EmployeeResponse mapToResponse(Employee employee) {
        EmployeeResponse response = new EmployeeResponse();
        response.setId(employee.getId());
        response.setName(employee.getName());
        response.setEmail(employee.getEmail());
        response.setPhone(employee.getPhone());
        
        if (employee.getUser() != null) {
            UserResponse userResponse = new UserResponse();
            userResponse.setId(employee.getUser().getId());
            userResponse.setUsername(employee.getUser().getUsername());
            response.setUser(userResponse);
        }
        return response;
    }

    private Employee mapToEntity(EmployeeRequest employeeRequest) {
        Employee employee = new Employee();
        employee.setName(employeeRequest.getName());
        employee.setEmail(employeeRequest.getEmail());
        employee.setPhone(employeeRequest.getPhone());
        return employee;
    }
}
