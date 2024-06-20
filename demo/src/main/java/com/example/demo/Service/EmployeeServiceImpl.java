package com.example.demo.Service;

import com.example.demo.DTO.EmployeeDTO;
import com.example.demo.DTO.PasswordResetDTO;
import com.example.demo.Repository.EmployeeRepo;
import com.example.demo.Service.OtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepo employeeRepo;

    @Autowired
    private OtpService otpService;



    @Override
    public String addEmployee(EmployeeDTO employeeDTO) {
        EmployeeDTO savedEmployee = employeeRepo.save(employeeDTO);
        return String.valueOf(savedEmployee.getId());
    }

    @Override
    public List<EmployeeDTO> getAllEmployees() {
        return employeeRepo.findAll();
    }

    @Override
    public EmployeeDTO getEmployeeById(int id) {
        return employeeRepo.findById(String.valueOf(id)).orElse(null);
    }

    @Override
    public void updateEmployee(int id, EmployeeDTO employeeDTO) {
        Optional<EmployeeDTO> existingEmployee = employeeRepo.findById(String.valueOf(id));
        if (existingEmployee.isPresent()) {
            EmployeeDTO employeeToUpdate = existingEmployee.get();
            employeeToUpdate.setEmail(employeeDTO.getEmail());
            employeeToUpdate.setPassword(employeeDTO.getPassword());
            employeeRepo.save(employeeToUpdate);
        }
    }

    @Override
    public void deleteEmployee(int id) {
        employeeRepo.deleteById(String.valueOf(id));
    }

    @Override
    public EmployeeDTO authenticate(String email, String password) {
        return employeeRepo.findByEmailAndPassword(email, password).orElse(null);
    }
    @Override
    public void sendResetLinkAndResetPassword(String email) {
        String resetToken = otpService.generateOtp();

        otpService.sendResetLinkEmail(email, resetToken);

    }

    public void updatePassword(PasswordResetDTO passwordResetDTO) {
        Optional<EmployeeDTO> employee = employeeRepo.findByResetToken(passwordResetDTO.getToken());

        if (employee.isPresent()) {
            EmployeeDTO employeeDTO = employee.get();
            employeeDTO.setPassword(passwordResetDTO.getNewPassword());
            employeeDTO.setResetToken("default");
            employeeRepo.save(employeeDTO);
        } else {
            throw new RuntimeException("Invalid reset token");
        }
    }
}