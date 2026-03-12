package com.christembassy.pune;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String phone;

    @Column(unique = true)
    private String macAddress;

    private String name;
    
    // NEW FIELD for Password
    private String password;

    private LocalDateTime registeredAt;

    // Getters and Setters for password
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    // Device fields
    private String deviceModel;
    private String deviceOsVersion;
    private String platform;

    // === ADD THESE MISSING FIELDS ===
    private String resetToken;
    private LocalDateTime tokenExpiry;

    private String resetOtp;
    private LocalDateTime otpExpiry;
   // This will store either the email OR the phone number
    // depending on what the user typed in during registration.
    @Column(name = "login_identifier", unique = true)
    private String loginIdentifier;
    public String getLoginIdentifier() { return loginIdentifier; }
    public void setLoginIdentifier(String loginIdentifier) { this.loginIdentifier = loginIdentifier; }
    // NEW FIELD
    private String role; // "USER" or "ADMIN"
    // Getters and Setters
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    // Getters and Setters for new fields
    public String getResetToken() { return resetToken; }
    public void setResetToken(String resetToken) { this.resetToken = resetToken; }

    public LocalDateTime getTokenExpiry() { return tokenExpiry; }
    public void setTokenExpiry(LocalDateTime tokenExpiry) { this.tokenExpiry = tokenExpiry; }

    public String getResetOtp() { return resetOtp; }
    public void setResetOtp(String resetOtp) { this.resetOtp = resetOtp; }

    public LocalDateTime getOtpExpiry() { return otpExpiry; }
    public void setOtpExpiry(LocalDateTime otpExpiry) { this.otpExpiry = otpExpiry; }

    // ... (Keep existing getters and setters) ...
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getMacAddress() { return macAddress; }
    public void setMacAddress(String macAddress) { this.macAddress = macAddress; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public LocalDateTime getRegisteredAt() { return registeredAt; }
    public void setRegisteredAt(LocalDateTime registeredAt) { this.registeredAt = registeredAt; }
    public String getDeviceModel() { return deviceModel; }
    public void setDeviceModel(String deviceModel) { this.deviceModel = deviceModel; }
    public String getDeviceOsVersion() { return deviceOsVersion; }
    public void setDeviceOsVersion(String deviceOsVersion) { this.deviceOsVersion = deviceOsVersion; }
    public String getPlatform() { return platform; }
    public void setPlatform(String platform) { this.platform = platform; }
}