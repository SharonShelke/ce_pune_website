package com.christembassy.pune;

import jakarta.persistence.*;
import java.time.DayOfWeek;
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

    @Column(name = "service_name")
    private String serviceName;

    public Attendance() {
        this.submissionTime = LocalDateTime.now();
    }

    public Attendance(User user, int count) {
        this(user, count, null);
    }

    public Attendance(User user, int count, String serviceName) {
        this.user = user;
        this.count = count;
        this.userName = user != null ? user.getName() : "Unknown";
        this.userEmail = user != null ? (user.getEmail() != null ? user.getEmail() : user.getLoginIdentifier()) : "Unknown";
        this.submissionTime = LocalDateTime.now();
        this.serviceName = (serviceName != null && !serviceName.trim().isEmpty())
                ? serviceName
                : determineServiceFromTime(this.submissionTime);
    }

    public static String determineServiceFromTime(LocalDateTime time) {
        if (time == null) return "General Service";
        DayOfWeek day = time.getDayOfWeek();
        int hour = time.getHour();
        int minute = time.getMinute();
        double timeVal = hour + (minute / 60.0);

        if (day == DayOfWeek.SUNDAY) {
            // Hindi Service: 6am - 8am (window 5:00 to 9:30)
            if (timeVal >= 5.0 && timeVal < 9.5) {
                return "Hindi Service (6am – 8am)";
            }
            // English Service: 10am - 12pm (window 9.5 to 14.0)
            else if (timeVal >= 9.5 && timeVal < 14.5) {
                return "English Service (10am – 12pm)";
            }
            // Marathi Service: 5pm - 7pm (window 15.5 to 20.5)
            else if (timeVal >= 15.5 && timeVal < 21.0) {
                return "Marathi Service (5pm – 7pm)";
            }
            return "Sunday Service";
        } else if (day == DayOfWeek.WEDNESDAY) {
            // Wednesday Lunch Hour Service: 1pm - 2pm (window 12.0 to 15.5)
            if (timeVal >= 12.0 && timeVal < 15.5) {
                return "Wednesday Lunch Hour Service (1pm – 2pm)";
            }
            // Wednesday Evening Service: 7pm - 9pm (window 18.0 to 22.5)
            else if (timeVal >= 18.0 && timeVal <= 23.0) {
                return "Wednesday Evening Service (7pm – 9pm)";
            }
            return "Wednesday Mid-Week Service";
        }
        return "Special Service (" + day.name() + ")";
    }

    public String getResolvedServiceName() {
        if (serviceName != null && !serviceName.trim().isEmpty()) {
            return serviceName;
        }
        return determineServiceFromTime(submissionTime);
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

    public String getServiceName() { return serviceName; }
    public void setServiceName(String serviceName) { this.serviceName = serviceName; }
}
