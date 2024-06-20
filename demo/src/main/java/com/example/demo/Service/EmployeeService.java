package com.example.demo.Service;

import com.example.demo.DTO.EmployeeDTO;
import com.example.demo.DTO.PasswordResetDTO;

import java.util.List;

public interface EmployeeService {
    String addEmployee(EmployeeDTO employeeDTO);
    List<EmployeeDTO> getAllEmployees();

    EmployeeDTO getEmployeeById(int id);

    void updateEmployee(int id, EmployeeDTO employeeDTO);

    void deleteEmployee(int id);



    EmployeeDTO authenticate(String email, String password);

    void sendResetLinkAndResetPassword(String email);

    public void updatePassword(PasswordResetDTO passwordResetDTO);
}
