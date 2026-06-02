package com.christembassy.pune;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private OtpService otpService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserRegistrationRequest request) {
        try {
            Optional<User> existing = Optional.empty();
            String actualIdentifierValue = null;

            if (request.getEmail() != null && !request.getEmail().isEmpty()) {
                existing = userRepository.findByEmail(request.getEmail());
                actualIdentifierValue = request.getEmail();
            } else if (request.getPhone() != null && !request.getPhone().isEmpty()) {
                existing = userRepository.findByPhone(request.getPhone());
                actualIdentifierValue = request.getPhone();
            }

            if (existing.isPresent()) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("{\"message\": \"User already exists\"}");
            }

            User user = new User();
            user.setEmail(request.getEmail());
            user.setPhone(request.getPhone());
            user.setLoginIdentifier(actualIdentifierValue);
            user.setMacAddress(request.getMacAddress());
            user.setName(request.getName());
            
            // --- HASH THE PASSWORD ---
            if (request.getPassword() != null) {
                user.setPassword(passwordEncoder.encode(request.getPassword()));
            }

            user.setRegisteredAt(LocalDateTime.now());
            user.setDeviceModel(request.getDeviceModel());
            user.setPlatform(request.getPlatoform());
            user.setRole("USER");

            User savedUser = userRepository.save(user);
            
            // Return token for immediate login after registration
            String token = jwtUtils.generateToken(actualIdentifierValue);
            savedUser.setCurrentSessionToken(token); // Or just return in a map
            userRepository.save(savedUser);

            return ResponseEntity.ok(savedUser);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"message\": \"Registration failed: " + e.getMessage() + "\"}");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginRequest request) {
        Optional<User> userOpt = Optional.empty();

        if (request.getEmail() != null) {
            userOpt = userRepository.findByEmail(request.getEmail());
        } else if (request.getPhone() != null) {
            userOpt = userRepository.findByPhone(request.getPhone());
        }

        if (userOpt.isPresent()) {
            User user = userOpt.get();
            
            // --- CHECK HASHED PASSWORD ---
            if (user.getPassword() != null && !passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("{\"message\": \"Invalid credentials\"}");
            }

            String token = jwtUtils.generateToken(user.getLoginIdentifier());
            user.setCurrentSessionToken(token);
            userRepository.save(user);

            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("{\"message\": \"Invalid credentials\"}");
        }
    }

    @PostMapping("/google-login")
    public ResponseEntity<?> googleLogin(@RequestBody GoogleLoginRequest request) {
        try {
            Optional<User> userOpt = userRepository.findByGoogleId(request.getGoogleId());
            if (userOpt.isEmpty() && request.getEmail() != null) {
                userOpt = userRepository.findByEmail(request.getEmail());
            }

            User user;
            if (userOpt.isPresent()) {
                user = userOpt.get();
                if (user.getGoogleId() == null) {
                    user.setGoogleId(request.getGoogleId());
                }
            } else {
                user = new User();
                user.setEmail(request.getEmail());
                user.setLoginIdentifier(request.getEmail());
                user.setName(request.getName());
                user.setGoogleId(request.getGoogleId());
                user.setRegisteredAt(LocalDateTime.now());
                user.setRole("USER");
            }

            user.setDeviceModel(request.getDeviceModel());
            user.setPlatform(request.getPlatform());
            user.setMacAddress(request.getMacAddress());
            
            String token = jwtUtils.generateToken(user.getLoginIdentifier() != null ? user.getLoginIdentifier() : request.getEmail());
            user.setCurrentSessionToken(token);
            
            User savedUser = userRepository.save(user);
            return ResponseEntity.ok(savedUser);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"message\": \"Google login failed: " + e.getMessage() + "\"}");
        }
    }

    @PostMapping("/kingschat-login")
    public ResponseEntity<?> kingschatLogin(@RequestBody KingsChatLoginRequest request) {
        try {
            Optional<User> userOpt = userRepository.findByKingschatId(request.getKingschatId());
            if (userOpt.isEmpty() && request.getEmail() != null) {
                userOpt = userRepository.findByEmail(request.getEmail());
            }
            if (userOpt.isEmpty() && request.getPhone() != null) {
                userOpt = userRepository.findByPhone(request.getPhone());
            }

            User user;
            if (userOpt.isPresent()) {
                user = userOpt.get();
                if (user.getKingschatId() == null) {
                    user.setKingschatId(request.getKingschatId());
                }
            } else {
                user = new User();
                user.setEmail(request.getEmail());
                user.setPhone(request.getPhone());
                user.setLoginIdentifier(request.getEmail() != null ? request.getEmail() : request.getPhone());
                user.setName(request.getName());
                user.setKingschatId(request.getKingschatId());
                user.setRegisteredAt(LocalDateTime.now());
                user.setRole("USER");
            }

            user.setDeviceModel(request.getDeviceModel());
            user.setPlatform(request.getPlatform());
            user.setMacAddress(request.getMacAddress());
            
            String token = jwtUtils.generateToken(user.getLoginIdentifier());
            user.setCurrentSessionToken(token);
            
            User savedUser = userRepository.save(user);
            return ResponseEntity.ok(savedUser);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"message\": \"KingsChat login failed: " + e.getMessage() + "\"}");
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
                    
                    user.setPassword(passwordEncoder.encode(request.newPassword));
                    user.setResetToken(null);
                    user.setTokenExpiry(null);
                    userRepository.save(user);
                    
                    if (user.getEmail() != null && !user.getEmail().trim().isEmpty()) {
                        try {
                            emailService.sendPasswordResetSuccessEmail(user.getEmail(), user.getName());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    return ResponseEntity.ok("Password reset successfully");
                }
            }
        } else if (request.phone != null && !request.phone.isEmpty()) {
             userOpt = userRepository.findByPhone(request.phone);
             if (userOpt.isPresent()) {
                 User user = userOpt.get();
                 if (request.otp != null && request.otp.equals(user.getResetOtp()) &&
                     user.getOtpExpiry() != null && user.getOtpExpiry().isAfter(LocalDateTime.now())) {
                     
                     user.setPassword(passwordEncoder.encode(request.newPassword));
                     user.setResetOtp(null);
                     user.setOtpExpiry(null);
                     userRepository.save(user);
                     
                     if (user.getEmail() != null && !user.getEmail().trim().isEmpty()) {
                         try {
                             emailService.sendPasswordResetSuccessEmail(user.getEmail(), user.getName());
                         } catch (Exception e) {
                             e.printStackTrace();
                         }
                     }
                     return ResponseEntity.ok("Password reset successfully");
                 }
             }
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid or expired reset token/OTP");
    }


}
