package com.example.demo.Config;

import com.example.demo.Service.OtpService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public OtpService otpService() {
        return new OtpService() {
            @Override
            public void sendResetLinkEmail(String email, String resetToken) {

            }

            @Override
            public void sendOtpByEmail(String email, String otp) {

            }

            @Override
            public String generateOtp() {
                return null;
            }
        };
    }
}
