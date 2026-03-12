package com.christembassy.pune;


import com.twilio.Twilio;
import com.twilio.type.PhoneNumber;
import com.twilio.rest.api.v2010.account.Message;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import jakarta.annotation.PostConstruct;

import java.util.Random;

@Service
public class OtpService {

    @Value("${twilio.account.sid}")
    private String accountSid;

    @Value("${twilio.auth.token}")
    private String authToken;

    @Value("${twilio.phone.number}")
    private String fromPhoneNumber;

    @PostConstruct
    public void init() {
        // Automatically connects to Twilio when your Spring server starts
        Twilio.init(accountSid, authToken);
    }

    // 1. Matches: String otp = otpService.generateOtp();
    public String generateOtp() {
        // Generates a random 6-digit number (e.g., 482910)
        return String.valueOf(100000 + new Random().nextInt(900000));
    }

    // 2. Matches: otpService.sendOtp(user.getPhone(), otp);
    public void sendOtp(String toPhoneNumber, String otpCode) {
        
        // Ensure India country code (+91) if it doesn't have one
        String formattedPhone = toPhoneNumber;
        if (formattedPhone != null && !formattedPhone.startsWith("+")) {
            formattedPhone = "+91" + formattedPhone; 
        }

        try {
            // Actually sends the SMS via Twilio
            Message message = Message.creator(
                    new PhoneNumber(formattedPhone),
                    new PhoneNumber(fromPhoneNumber), 
                    "Your Christ Embassy Pune reset code is: " + otpCode
            ).create();
            
            System.out.println("OTP sent successfully to " + formattedPhone);
            
        } catch (Exception e) {
            System.err.println("Failed to send OTP to " + formattedPhone + ". Error: " + e.getMessage());
            // You can choose to throw an exception here if you want the API to return an error, 
            // but logging it is fine for now.
        }
    }
}
