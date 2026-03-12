package com.christembassy.pune;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.Optional;

    public interface UserRepository extends JpaRepository<User, Long> {
        Optional<User> findByEmail(String email);
        Optional<User> findByPhone(String phone);
        Optional<User> findByMacAddress(String macAddress);

        // 🔍 Find user by loginIdentifier and OTP if not expired
        @Query("SELECT u FROM User u WHERE u.loginIdentifier = :identifier AND u.resetOtp = :otp AND u.otpExpiry > :now")
        Optional<User> findValidOtpUser(String identifier, String otp, LocalDateTime now);

        // 🔍 Find user by loginIdentifier and token if not expired
        @Query("SELECT u FROM User u WHERE u.loginIdentifier = :identifier AND u.resetToken = :token AND u.tokenExpiry > :now")
        Optional<User> findValidTokenUser(String identifier, String token, LocalDateTime now);

        // 🆕 The new unified identifier search
        Optional<User> findByLoginIdentifier(String loginIdentifier);
    }


