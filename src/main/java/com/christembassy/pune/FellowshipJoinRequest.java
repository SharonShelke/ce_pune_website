package com.christembassy.pune;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "fellowship_join_requests")
public class FellowshipJoinRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "fellowship_id", nullable = false)
    private Fellowship fellowship;

    private String userName;
    private String userEmail;
    private String userPhone;
    private String userAddress;
    private String message;
    private String sharedWithEmails;

    private String status; // PENDING, APPROVED, REJECTED
    private boolean emailSent;
    private LocalDateTime requestDate;

    public FellowshipJoinRequest() {
        this.requestDate = LocalDateTime.now();
        this.status = "PENDING";
        this.emailSent = false;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Fellowship getFellowship() { return fellowship; }
    public void setFellowship(Fellowship fellowship) { this.fellowship = fellowship; }
    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }
    public String getUserEmail() { return userEmail; }
    public void setUserEmail(String userEmail) { this.userEmail = userEmail; }
    public String getUserPhone() { return userPhone; }
    public void setUserPhone(String userPhone) { this.userPhone = userPhone; }
    public String getUserAddress() { return userAddress; }
    public void setUserAddress(String userAddress) { this.userAddress = userAddress; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public String getSharedWithEmails() { return sharedWithEmails; }
    public void setSharedWithEmails(String sharedWithEmails) { this.sharedWithEmails = sharedWithEmails; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public boolean isEmailSent() { return emailSent; }
    public void setEmailSent(boolean emailSent) { this.emailSent = emailSent; }
    public LocalDateTime getRequestDate() { return requestDate; }
    public void setRequestDate(LocalDateTime requestDate) { this.requestDate = requestDate; }
}
