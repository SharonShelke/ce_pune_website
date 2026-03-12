package com.christembassy.pune;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/auth")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    EmailService emailService;

    @Autowired
    OtpService otpService;

    @PostMapping("/register")
    public User register(@RequestBody UserRegistrationRequest request) {

        Optional<User> existing = Optional.empty();
        String identifierType = null; // Will be set to "EMAIL" or "PHONE"

        // 1. Check if they provided an Email
        if (request.getEmail() != null && !request.getEmail().isEmpty()) {
            existing = userRepository.findByEmail(request.getEmail());
            identifierType = "EMAIL";

            // 2. Or check if they provided a Phone number
        } else if (request.getPhone() != null && !request.getPhone().isEmpty()) {
            existing = userRepository.findByPhone(request.getPhone());
            identifierType = "PHONE";

            // 3. Fallback to MAC Address
        } else if (request.getMacAddress() != null) {
            existing = userRepository.findByMacAddress(request.getMacAddress());
            identifierType = "MAC_ADDRESS";
        }

        // If user is already found in DB, return them
        if (existing.isPresent()) {
            return existing.get();
        }

        // --- NEW USER CREATION ---
        User user = new User();

        // Save the actual values in their correct columns
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());

        // 👉 Set the new column to just SAY what type of registration this was
        // It will store "EMAIL", "PHONE", or "MAC_ADDRESS"
        user.setLoginIdentifier(identifierType);

        user.setMacAddress(request.getMacAddress());
        user.setName(request.getName());
        user.setRegisteredAt(LocalDateTime.now());
        user.setDeviceModel(request.getDeviceModel());
        user.setPlatform(request.getPlatoform());
        user.setRole("USER");

        return userRepository.save(user);
    }



    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginRequest request) {
        Optional<User> user = Optional.empty();

        // It simply checks the correct column based on what was provided
        if (request.getEmail() != null) {
            user = userRepository.findByEmail(request.getEmail());
        } else if (request.getPhone() != null) {
            user = userRepository.findByPhone(request.getPhone());
        } else if (request.getMacAddress() != null) {
            user = userRepository.findByMacAddress(request.getMacAddress());
        }

        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("{\"message\": \"Invalid credentials\"}");
        }
    }



    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }


    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody ForgotPasswordRequest request) {
        Optional<User> userOpt = Optional.empty();

        // 1. Find the user by Email OR Phone
        if (request.getEmail() != null && !request.getEmail().isEmpty()) {
            userOpt = userRepository.findByEmail(request.getEmail());
        } else if (request.getPhone() != null && !request.getPhone().isEmpty()) {
            userOpt = userRepository.findByPhone(request.getPhone());
        }

        if (userOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        User user = userOpt.get();

        // 2. Handle Email Reset
        if (request.getEmail() != null && !request.getEmail().isEmpty()) {
            String resetToken = UUID.randomUUID().toString(); // Standard long token for URLs

            user.setResetToken(resetToken);
            user.setTokenExpiry(LocalDateTime.now().plusMinutes(15)); // Valid for 15 mins
            userRepository.save(user); // Save to database!

            emailService.sendResetLink(user.getEmail(), resetToken, user.getName());
            return ResponseEntity.ok("Reset link sent to email");

            // 3. Handle Phone OTP Reset
        } else if (request.getPhone() != null && !request.getPhone().isEmpty()) {
            String otp = otpService.generateOtp(); // E.g. "123456"

            user.setResetOtp(otp);
            user.setOtpExpiry(LocalDateTime.now().plusMinutes(10)); // Valid for 10 mins
            userRepository.save(user); // Save to database!

            otpService.sendOtp(user.getPhone(), otp);
            return ResponseEntity.ok("OTP sent to phone");
        }

        return ResponseEntity.badRequest().body("Email or phone required");
    }

    static class ResetPasswordRequest {
        public String email;
        public String phone;
        public String token;
        public String otp;
        public String newPassword;
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordRequest request) {
        if (request.newPassword == null || request.newPassword.isEmpty()) {
            return ResponseEntity.badRequest().body("New password is required");
        }

        Optional<User> userOpt = Optional.empty();

        if (request.email != null && !request.email.isEmpty()) {
            userOpt = userRepository.findByEmail(request.email);
            if (userOpt.isPresent()) {
                User user = userOpt.get();
                if (request.token != null && request.token.equals(user.getResetToken()) && 
                    user.getTokenExpiry() != null && user.getTokenExpiry().isAfter(LocalDateTime.now())) {
                    
                    user.setPassword(request.newPassword);
                    user.setResetToken(null);
                    user.setTokenExpiry(null);
                    userRepository.save(user);
                    return ResponseEntity.ok("Password reset successfully");
                }
            }
        } else if (request.phone != null && !request.phone.isEmpty()) {
             userOpt = userRepository.findByPhone(request.phone);
             if (userOpt.isPresent()) {
                 User user = userOpt.get();
                 if (request.otp != null && request.otp.equals(user.getResetOtp()) &&
                     user.getOtpExpiry() != null && user.getOtpExpiry().isAfter(LocalDateTime.now())) {
                     
                     user.setPassword(request.newPassword);
                     user.setResetOtp(null);
                     user.setOtpExpiry(null);
                     userRepository.save(user);
                     return ResponseEntity.ok("Password reset successfully");
                 }
             }
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid or expired reset token/OTP");
    }


}