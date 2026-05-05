package com.christembassy.pune;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "foundation_enrollments")
public class FoundationEnrollment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String phone;
    private String status; // PENDING, APPROVED
    private boolean emailSent;
    private String sharedWithEmails;
    private LocalDateTime requestDate;

    public FoundationEnrollment() {
        this.requestDate = LocalDateTime.now();
        this.status = "PENDING";
        this.emailSent = false;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public boolean isEmailSent() { return emailSent; }
    public void setEmailSent(boolean emailSent) { this.emailSent = emailSent; }
    public String getSharedWithEmails() { return sharedWithEmails; }
    public void setSharedWithEmails(String sharedWithEmails) { this.sharedWithEmails = sharedWithEmails; }
    public LocalDateTime getRequestDate() { return requestDate; }
    public void setRequestDate(LocalDateTime requestDate) { this.requestDate = requestDate; }
}
