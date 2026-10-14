package com.christembassy.pune;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AttendanceDTO {
    private Long id;
    private String userName;
    private String userEmail;
    private String platform;
    private int count;
    private LocalDateTime submissionTime;
    private String serviceName;
    private String date;
    private String formattedTime;
    private String formattedDateTime;

    private static final DateTimeFormatter DATE_FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter TIME_FMT = DateTimeFormatter.ofPattern("hh:mm a");
    private static final DateTimeFormatter DATE_TIME_FMT = DateTimeFormatter.ofPattern("dd MMM yyyy, hh:mm a");

    public AttendanceDTO(Long id, String userName, String userEmail, String platform, int count, LocalDateTime submissionTime, String serviceName) {
        this.id = id;
        this.userName = userName;
        this.userEmail = userEmail;
        this.platform = platform;
        this.count = count;
        this.submissionTime = submissionTime;
        this.serviceName = serviceName;
        if (submissionTime != null) {
            this.date = submissionTime.format(DATE_FMT);
            this.formattedTime = submissionTime.format(TIME_FMT);
            this.formattedDateTime = submissionTime.format(DATE_TIME_FMT);
        } else {
            this.date = "";
            this.formattedTime = "";
            this.formattedDateTime = "";
        }
    }

    public Long getId() { return id; }
    public String getUserName() { return userName; }
    public String getUserEmail() { return userEmail; }
    public String getPlatform() { return platform; }
    public int getCount() { return count; }
    public LocalDateTime getSubmissionTime() { return submissionTime; }
    public String getServiceName() { return serviceName; }
    public String getDate() { return date; }
    public String getFormattedTime() { return formattedTime; }
    public String getFormattedDateTime() { return formattedDateTime; }
}
