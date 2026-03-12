package com.christembassy.pune;

public class UserRegistrationRequest {
    private String email;
    private String phone;
    private String macAddress;
    private String name;
    private  String deviceModel;
    private  String platoform;

    public void setDeviceModel(String deviceModel) {
        this.deviceModel = deviceModel;
    }

    public String getPlatoform() {
        return platoform;
    }

    public void setPlatoform(String platoform) {
        this.platoform = platoform;
    }

    public String getDeviceModel() {
        return deviceModel;
    }

    // Getters and setters
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getMacAddress() { return macAddress; }
    public void setMacAddress(String macAddress) { this.macAddress = macAddress; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
} 