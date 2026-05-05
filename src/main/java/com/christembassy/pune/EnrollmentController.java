package com.christembassy.pune;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/enrollment")
public class EnrollmentController {

    @Autowired
    private EmailService emailService;

    @Autowired
    private FoundationEnrollmentRepository foundationEnrollmentRepository;

    @PostMapping("/request")
    public ResponseEntity<String> submitEnrollmentRequest(@RequestBody EnrollmentRequest request) {
        FoundationEnrollment enrollment = new FoundationEnrollment();
        enrollment.setName(request.getName());
        enrollment.setPhone(request.getPhone());
        enrollment.setEmail(request.getEmail());
        
        String adminEmails = "Chifitahjoy@gmail.com, sharonshelke1@Gmail.com";
        enrollment.setSharedWithEmails(adminEmails);

        try {
            emailService.sendEnrollmentRequest(
                    request.getName(),
                    request.getPhone(),
                    request.getEmail()
            );
            enrollment.setEmailSent(true);
        } catch (Exception e) {
            enrollment.setEmailSent(false);
            System.err.println("Failed to send enrollment email: " + e.getMessage());
        }

        foundationEnrollmentRepository.save(enrollment);
        return ResponseEntity.ok("Enrollment request sent successfully.");
    }
}
