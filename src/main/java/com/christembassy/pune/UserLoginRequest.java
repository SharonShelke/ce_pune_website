package com.christembassy.pune;

public class UserLoginRequest {
    private String email;
    private String phone;
    private String macAddress;

    private String password;

    // Getters and setters
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getMacAddress() { return macAddress; }
    public void setMacAddress(String macAddress) { this.macAddress = macAddress; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
} 