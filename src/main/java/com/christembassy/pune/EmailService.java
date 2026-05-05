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

        String resetUrl = "http://15.206.166.139/reset-password?token=" + resetToken + "&email=" + toEmail;
        String greetingName = (name != null && !name.trim().isEmpty()) ? name : "";

        message.setText("Hi " + greetingName + "\n\n" +
                "You recently requested to reset your password. " +
                "Please click the link below to set a new password:\n\n" +
                resetUrl + "\n\n" +
                "If you did not request this, please ignore this email.\n\n" +
                "God Bless,\nChrist Embassy Pune");

        mailSender.send(message);
    }

    public void sendEnrollmentRequest(String name, String phone, String email) {
        SimpleMailMessage message = new SimpleMailMessage();
        String[] recipients = {"Chifitahjoy@gmail.com", "sharonshelke1@Gmail.com"};
        message.setTo(recipients);
        message.setSubject("New Enrollment Request: " + name);

        message.setText("Dear Administration,\n\n" +
                "A new enrollment request has been received from the website.\n\n" +
                "Details:\n" +
                "Name: " + name + "\n" +
                "Phone: " + phone + "\n" +
                "Email: " + email + "\n\n" +
                "Please follow up with the applicant as soon as possible.\n\n" +
                "Regards,\n" +
                "CE Pune Website");

        mailSender.send(message);
    }

    public void sendFellowshipJoinNotification(String cellName, String name, String email, String phone, String messageContent) {
        SimpleMailMessage message = new SimpleMailMessage();
        String[] recipients = {"7jbking0102@gmail.com", "sharonshelke1@gmail.com"};
        message.setTo(recipients);
        message.setSubject("Fellowship Join Request: " + cellName);

        message.setText("Dear Administration,\n\n" +
                "A new request to join a fellowship has been received.\n\n" +
                "Fellowship: " + cellName + "\n" +
                "Applicant: " + name + "\n" +
                "Email: " + email + "\n" +
                "Phone: " + phone + "\n" +
                "Message: " + (messageContent != null ? messageContent : "N/A") + "\n\n" +
                "Please coordinate with the cell leader for further action.\n\n" +
                "Regards,\n" +
                "CE Pune Website");

        mailSender.send(message);
    }
}
