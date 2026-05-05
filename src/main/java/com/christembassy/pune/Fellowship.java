package com.christembassy.pune;

import jakarta.persistence.*;

@Entity
@Table(name = "fellowships")
public class Fellowship {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String leaderName;
    private String phoneNumber;
    private String location;
    private String meetingTime;
    private String description;

    public Fellowship() {}

    public Fellowship(String name, String leaderName, String phoneNumber, String location, String meetingTime, String description) {
        this.name = name;
        this.leaderName = leaderName;
        this.phoneNumber = phoneNumber;
        this.location = location;
        this.meetingTime = meetingTime;
        this.description = description;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getLeaderName() { return leaderName; }
    public void setLeaderName(String leaderName) { this.leaderName = leaderName; }
    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public String getMeetingTime() { return meetingTime; }
    public void setMeetingTime(String meetingTime) { this.meetingTime = meetingTime; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
