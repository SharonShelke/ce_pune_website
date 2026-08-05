package com.christembassy.pune;
import java.time.LocalDateTime;
public class AttendanceDTO {
    private String userName;
    private String userEmail;
    private String platform;
    private int count;
    private LocalDateTime submissionTime;
    public AttendanceDTO(String userName, String userEmail, String platform, int count, LocalDateTime submissionTime) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.platform = platform;
        this.count = count;
        this.submissionTime = submissionTime;
    }
    
    // Getters
    public String getUserName() { return userName; }
    public String getUserEmail() { return userEmail; }
    public String getPlatform() { return platform; }
    public int getCount() { return count; }
    public LocalDateTime getSubmissionTime() { return submissionTime; }
}
