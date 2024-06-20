package com.example.demo.Service;

import com.example.demo.DTO.EmployeeDTO;
import com.example.demo.Repository.EmployeeRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;
import java.util.UUID;

@Service
public class OtpServiceImpl implements OtpService {

    private static final Logger logger = LoggerFactory.getLogger(OtpServiceImpl.class);

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private EmployeeRepo employeeRepo;


    private String globalOtp; // Global variable to store the OTP

    // Method to generate OTP
    public String generateOtp() {
        // Generate a random 6-digit OTP
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);
        globalOtp = String.valueOf(otp); // Store the OTP in the global variable
        return globalOtp;
    }

    // Method to send OTP by email
    public void sendOtpByEmail(String email, String otp) {
        // Create an email message
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("OTP for Registration");
        message.setText("Your OTP for registration is: " + otp);

        // Send the email
        try {
            emailSender.send(message);
            logger.info("OTP email sent successfully to {}", email);
        } catch (Exception e) {
            logger.error("Failed to send OTP email to {}", email, e);
        }
    }

    // Method to verify OTP
    public boolean verifyOtp(String enteredOtp) {
        // Compare the entered OTP with the global OTP value
        return enteredOtp.equals(globalOtp);
    }

    @Override
    public void sendResetLinkEmail(String email, String resetToken) {
        Optional<EmployeeDTO> employee = employeeRepo.findByEmail(email);

        if (employee.isPresent()) {
            String token = UUID.randomUUID().toString();
            // Save the token in the database (you need to add a field for it in the Employee entity)
            EmployeeDTO employeeDTO = employee.get();
            employeeDTO.setResetToken(token);
            employeeRepo.save(employeeDTO);

            String resetLink = "http://localhost:4200/newpass?token=" + token;

            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(email);
            message.setSubject("Password Reset Request");
            message.setText("The token is: " + token);
            message.setText("Click the link to reset your password: " + resetLink);

            emailSender.send(message);
        } else {
            throw new RuntimeException("Email not found");
        }
    }
}
