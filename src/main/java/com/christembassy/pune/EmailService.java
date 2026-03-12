package com.christembassy.pune;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendResetLink(String toEmail, String resetToken, String name) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Reset Your Christ Embassy Pune Password");

        // Let's create a clickable link!
        // Note: Change localhost:3000 to your actual frontend domain when you deploy
        String resetUrl = "http://localhost:3000/reset-password?token=" + resetToken + "&email=" + toEmail;
        
        String greetingName = (name != null && !name.trim().isEmpty()) ? name : "";

        message.setText("Hi " + greetingName + "\n\n" +
                "You recently requested to reset your password. " +
                "Please click the link below to set a new password:\n\n" +
                resetUrl + "\n\n" +
                "If you did not request this, please ignore this email.\n\n" +
                "God Bless,\nChrist Embassy Pune");

        mailSender.send(message);



    }
}
