package com.christembassy.pune;
import jakarta.persistence.*;
import java.time.LocalDateTime;
@Entity
@Table(name = "attendance")
public class Attendance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    private int count;
    @Column(name = "submission_time")
    private LocalDateTime submissionTime;
    @Column(name = "user_name")
    private String userName;
    
    @Column(name = "user_email")
    private String userEmail;

    public Attendance() {
        this.submissionTime = LocalDateTime.now();
    }
    public Attendance(User user, int count) {
        this.user = user;
        this.count = count;
        this.userName = user.getName();
        this.userEmail = user.getEmail() != null ? user.getEmail() : user.getLoginIdentifier();
        this.submissionTime = LocalDateTime.now();
    }
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    public int getCount() { return count; }
    public void setCount(int count) { this.count = count; }
    public LocalDateTime getSubmissionTime() { return submissionTime; }
    public void setSubmissionTime(LocalDateTime submissionTime) { this.submissionTime = submissionTime; }
    
    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }
    
    public String getUserEmail() { return userEmail; }
    public void setUserEmail(String userEmail) { this.userEmail = userEmail; }
}
