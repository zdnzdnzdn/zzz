package com.example.testserverapp.service;

import com.example.testserverapp.entity.Employee;
import com.example.testserverapp.entity.Role;
import com.example.testserverapp.entity.User;
import com.example.testserverapp.model.request.LoginRequest;
import com.example.testserverapp.model.request.RegistrationRequest;
import com.example.testserverapp.model.response.LoginResponse;
import com.example.testserverapp.repository.EmployeeRepository;
import com.example.testserverapp.repository.UserRepository;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthService {

    private EmployeeRepository employeeRepository;
    private EmployeeService employeeService;
    private RoleService roleService;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authManager;
    private AppUserDetailService appUserDetailService;

    public Employee registration(RegistrationRequest registrationRequest) {
        // Setup data for employee & user
        Employee employee = new Employee();
        User user = new User();

        // Copy properties from registrationRequest to employee and user
        BeanUtils.copyProperties(registrationRequest, employee);
        BeanUtils.copyProperties(registrationRequest, user);

        // Encode the password
        user.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));

        // Set default role (assuming role with ID 1 is the default role)
        List<Role> roles = Collections.singletonList(roleService.getById(1));
        user.setRoles(roles);

        // Set up the relationship between user and employee
        user.setEmployee(employee);
        employee.setUser(user);

        // Save the employee (which will also save the user due to cascade)
        return employeeRepository.save(employee);
    }

    public Employee addRole(Integer idEmployee, Role role) {
        // Check if employee exists
        Employee employee = employeeService.getById(idEmployee);
        User user = employee.getUser();

        // Get current roles and add the new role
        List<Role> roles = user.getRoles();
        roles.add(roleService.getById(role.getId()));

        // Set updated roles to user and save
        user.setRoles(roles);
        userRepository.save(user);

        return employee;
    }

    public Employee updateRole(Integer idEmployee, Integer roleId, Role updatedRole) {
        // Retrieve the employee from the database
        Employee employee = employeeService.getById(idEmployee);
        User user = employee.getUser();

        // Find the role to update
        List<Role> roles = user.getRoles();
        for (Role role : roles) {
            if (role.getId().equals(roleId)) {
                // Update the role's properties
                role.setName(updatedRole.getName());
                // You may want to update other properties of the role here
                break; // Exit loop once the role is updated
            }
        }

        // Save the updated user with the modified roles
        userRepository.save(user);
        return employee; // Return the updated employee
    }

    public void deleteRole(Integer idEmployee, Integer roleId) {
        // Retrieve the employee from the database
        Employee employee = employeeService.getById(idEmployee);
        User user = employee.getUser();

        // Find and remove the role
        List<Role> roles = user.getRoles();
        roles.removeIf(role -> role.getId().equals(roleId)); // Remove the role by ID

        // Save the updated user with the modified roles
        userRepository.save(user);
    }

    public LoginResponse login(LoginRequest loginRequest) {
        // Authentication
        UsernamePasswordAuthenticationToken authReq = new UsernamePasswordAuthenticationToken(
            loginRequest.getUsername(),
            loginRequest.getPassword()
        );

        // Set principle
        Authentication auth = authManager.authenticate(authReq);
        SecurityContext sc = SecurityContextHolder.getContext();
        sc.setAuthentication(auth);

        // Get username for login response
        UserDetails userDetails = appUserDetailService.loadUserByUsername(
            loginRequest.getUsername()
        );

        // Get email for login response
        User user = userRepository
            .findByUsernameOrEmployeeEmail(
                loginRequest.getUsername(),
                loginRequest.getUsername()
            )
            .get();

        // Get authorities for login response
        List<String> authorities = userDetails
            .getAuthorities()
            .stream()
            .map(authority -> authority .getAuthority())
            .collect(Collectors.toList());

        // Set response
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setUsername(userDetails.getUsername());
        loginResponse.setEmail(user.getEmployee().getEmail());
        loginResponse.setAuthorities(authorities);

        return loginResponse;
    }
}