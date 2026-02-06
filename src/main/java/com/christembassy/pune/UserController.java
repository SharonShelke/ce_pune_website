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
        // Check if user exists by email, phone, or MAC address
        Optional<User> existing = Optional.empty();
        if (request.getEmail() != null) {
            existing = userRepository.findByEmail(request.getEmail());
        } else if (request.getPhone() != null) {
            existing = userRepository.findByPhone(request.getPhone());
        } else if (request.getMacAddress() != null) {
            existing = userRepository.findByMacAddress(request.getMacAddress());
        }
        if (existing.isPresent()) {
            return existing.get(); // Already registered
        }
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        user.setMacAddress(request.getMacAddress());
        user.setName(request.getName());
        user.setRegisteredAt(LocalDateTime.now());
        user.setDeviceModel(request.getDeviceModel());
        user.setPlatform(request.getPlatoform());
        return userRepository.save(user);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginRequest request) {
        Optional<User> user = Optional.empty();
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

        if (request.getEmail() != null) {
            userOpt = userRepository.findByEmail(request.getEmail());
        } else if (request.getPhone() != null) {
            userOpt = userRepository.findByPhone(request.getPhone());
        }

        if (userOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        User user = userOpt.get();
        String resetTokenOrOtp = UUID.randomUUID().toString().substring(0, 6);

        if (request.getEmail() != null) {
            emailService.sendResetLink(user.getEmail(), resetTokenOrOtp);
            return ResponseEntity.ok("Reset link sent to email");
        } else if (request.getPhone() != null) {
            String otp = otpService.generateOtp();
            otpService.sendOtp(user.getPhone(), otp);
            return ResponseEntity.ok("OTP sent to phone");
        }

        return ResponseEntity.badRequest().body("Email or phone required");
    }


}