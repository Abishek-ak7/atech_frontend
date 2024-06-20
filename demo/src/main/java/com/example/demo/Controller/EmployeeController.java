package com.example.demo.Controller;


import com.example.demo.DTO.EmailRequestDTO;
import com.example.demo.DTO.EmployeeDTO;
import com.example.demo.DTO.OtpVerificationDTO;
import com.example.demo.DTO.PasswordResetDTO;
import com.example.demo.Service.EmployeeService;
import com.example.demo.Service.OtpServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;
import com.example.demo.Repository.EmployeeRepo;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("api/v1/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired EmployeeRepo employeeRepo;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private OtpServiceImpl otpService;

    @PostMapping("/register")
    public String saveEmployee(@RequestBody EmployeeDTO employeeDTO) {

        Optional<EmployeeDTO> Existing_Employee =  employeeRepo.findByEmail(employeeDTO.getEmail());
        if (Existing_Employee.isPresent()){
            throw new IllegalArgumentException ("Email is already exists");
        }
        else{
            String otp = otpService.generateOtp();

            try {
                // Add employee data to the database
                String result = employeeService.addEmployee(employeeDTO);

                // Send OTP via email
                otpService.sendOtpByEmail(employeeDTO.getEmail(), otp);

                return result;
            } catch (Exception e) {
                // Handle exception
                // For example, log the error
                e.printStackTrace();

                // You might want to throw or return a custom error message here
                return "An error occurred while processing the request.";
            }


            // return employeeService.addEmployee(employeeDTO);
        }
    }


    @PostMapping("/verify-otp")
    public boolean VertifyOTP(@RequestBody OtpVerificationDTO otpVerificationDTO){
        return otpService.verifyOtp(otpVerificationDTO.getOtp());

    }

    @PostMapping("/login")
    public EmployeeDTO login(@RequestBody EmployeeDTO employeeDTO) {
        EmployeeDTO authenticatedEmployee = employeeService.authenticate(employeeDTO.getEmail(), employeeDTO.getPassword());
        if (authenticatedEmployee != null) {
            return authenticatedEmployee;
        } else {
            throw new RuntimeException("Invalid email or password");
        }
    }

    @GetMapping
    public List<EmployeeDTO> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @GetMapping("/{id}")
    public EmployeeDTO getEmployeeById(@PathVariable int id) {
        return employeeService.getEmployeeById(id);
    }

    @PutMapping("/{id}")
    public String updateEmployee(@PathVariable int id, @RequestBody EmployeeDTO employeeDTO) {
        employeeService.updateEmployee(id, employeeDTO);
        return "Employee updated successfully";
    }

    @DeleteMapping("/{id}")
    public String deleteEmployee(@PathVariable int id) {
        employeeService.deleteEmployee(id);
        return "Employee deleted successfully";
    }
    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody EmailRequestDTO emailRequestDTO) {
        try {
            String email = emailRequestDTO.getEmail();
            Optional<EmployeeDTO> existingEmployee = employeeRepo.findByEmail(email);

            if (existingEmployee.isPresent()) {
                String otp = otpService.generateOtp();
                otpService.sendResetLinkEmail(email, otp);
                return ResponseEntity.ok("Reset link sent successfully");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Email not found");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to send reset link: " + e.getMessage());
        }


    }
    @PostMapping("/update-password")
    public ResponseEntity<String> updatePassword(@RequestBody PasswordResetDTO passwordResetDTO) {
        try {
            employeeService.updatePassword(passwordResetDTO);
            return ResponseEntity.ok("Password reset successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to reset password: " + e.getMessage());
        }
    }
}

