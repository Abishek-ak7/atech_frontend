package com.example.demo.Service;

public interface OtpService {
    void sendResetLinkEmail(String email, String resetToken);
    void sendOtpByEmail(String email,String otp);
    String generateOtp();
}
