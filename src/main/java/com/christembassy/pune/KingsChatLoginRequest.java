package com.christembassy.pune;

public class KingsChatLoginRequest {
    private String email;
    private String name;
    private String kingschatId;
    private String phone;
    
    // Device info
    private String deviceModel;
    private String platform;
    private String macAddress;

    // Getters and Setters
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getKingschatId() { return kingschatId; }
    public void setKingschatId(String kingschatId) { this.kingschatId = kingschatId; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getDeviceModel() { return deviceModel; }
    public void setDeviceModel(String deviceModel) { this.deviceModel = deviceModel; }

    public String getPlatform() { return platform; }
    public void setPlatform(String platform) { this.platform = platform; }

    public String getMacAddress() { return macAddress; }
    public void setMacAddress(String macAddress) { this.macAddress = macAddress; }
}
