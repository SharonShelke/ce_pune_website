package com.christembassy.pune;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByPhone(String phone);
    Optional<User> findByMacAddress(String macAddress);

    // 🔍 Find user by phone and OTP if not expired
    @Query("SELECT u FROM User u WHERE u.phone = :phone AND u.resetOtp = :otp AND u.otpExpiry > :now")
    Optional<User> findValidOtpUser(String phone, String otp, LocalDateTime now);

    // 🔍 Find user by email and token if not expired
    @Query("SELECT u FROM User u WHERE u.email = :email AND u.resetToken = :token AND u.tokenExpiry > :now")
    Optional<User> findValidTokenUser(String email, String token, LocalDateTime now);
}
