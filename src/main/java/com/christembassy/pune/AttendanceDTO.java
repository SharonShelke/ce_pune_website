package com.christembassy.pune;
import java.time.LocalDateTime;
public class AttendanceDTO {
    private String userName;
    private String platform;
    private int count;
    private LocalDateTime submissionTime;
    public AttendanceDTO(String userName, String platform, int count, LocalDateTime submissionTime) {
        this.userName = userName;
        this.platform = platform;
        this.count = count;
        this.submissionTime = submissionTime;
    }
    
    // Getters
    public String getUserName() { return userName; }
    public String getPlatform() { return platform; }
    public int getCount() { return count; }
    public LocalDateTime getSubmissionTime() { return submissionTime; }
}