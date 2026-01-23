package com.christembassy.pune;

import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class OtpService {
    public String generateOtp() {
        return String.valueOf(100000 + new Random().nextInt(900000));
    }

    public void sendOtp(String phone, String otp) {
        // You can integrate with real SMS providers like Twilio here
        System.out.println("Sending OTP " + otp + " to phone: " + phone);
    }
}
