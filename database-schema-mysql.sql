CREATE DATABASE IF NOT EXISTS `cepune2`
  DEFAULT CHARACTER SET utf8mb4
  DEFAULT COLLATE utf8mb4_0900_ai_ci;

USE `cepune2`;

CREATE TABLE IF NOT EXISTS `users` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `current_session_token` VARCHAR(255) DEFAULT NULL,
  `device_model` VARCHAR(255) DEFAULT NULL,
  `device_os_version` VARCHAR(255) DEFAULT NULL,
  `email` VARCHAR(255) DEFAULT NULL,
  `google_id` VARCHAR(255) DEFAULT NULL,
  `kingschat_id` VARCHAR(255) DEFAULT NULL,
  `login_identifier` VARCHAR(255) DEFAULT NULL,
  `mac_address` VARCHAR(255) DEFAULT NULL,
  `name` VARCHAR(255) DEFAULT NULL,
  `otp_expiry` DATETIME(6) DEFAULT NULL,
  `password` VARCHAR(255) DEFAULT NULL,
  `phone` VARCHAR(255) DEFAULT NULL,
  `platform` VARCHAR(255) DEFAULT NULL,
  `registered_at` DATETIME(6) DEFAULT NULL,
  `reset_otp` VARCHAR(255) DEFAULT NULL,
  `reset_token` VARCHAR(255) DEFAULT NULL,
  `role` VARCHAR(255) DEFAULT NULL,
  `token_expiry` DATETIME(6) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_users_email` (`email`),
  UNIQUE KEY `uk_users_phone` (`phone`),
  UNIQUE KEY `uk_users_mac_address` (`mac_address`),
  UNIQUE KEY `uk_users_login_identifier` (`login_identifier`),
  KEY `idx_users_google_id` (`google_id`),
  KEY `idx_users_kingschat_id` (`kingschat_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `fellowships` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) DEFAULT NULL,
  `leader_name` VARCHAR(255) DEFAULT NULL,
  `phone_number` VARCHAR(255) DEFAULT NULL,
  `location` VARCHAR(255) DEFAULT NULL,
  `state` VARCHAR(255) DEFAULT NULL,
  `city` VARCHAR(255) DEFAULT NULL,
  `meeting_time` VARCHAR(255) DEFAULT NULL,
  `description` VARCHAR(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `announcements` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(255) DEFAULT NULL,
  `subtitle` VARCHAR(255) DEFAULT NULL,
  `icon` VARCHAR(255) DEFAULT NULL,
  `active` BIT(1) NOT NULL DEFAULT b'1',
  `display_order` INT NOT NULL DEFAULT 0,
  `created_at` DATETIME(6) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_announcements_active_order` (`active`, `display_order`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `songs` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(255) DEFAULT NULL,
  `artist` VARCHAR(255) DEFAULT NULL,
  `category` VARCHAR(255) DEFAULT NULL,
  `icon` VARCHAR(255) DEFAULT NULL,
  `drive_id` VARCHAR(255) DEFAULT NULL,
  `video_drive_id` VARCHAR(255) DEFAULT NULL,
  `description` TEXT DEFAULT NULL,
  `lyrics` TEXT DEFAULT NULL,
  `teaching_notes` TEXT DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_songs_category` (`category`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `attendance` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `count` INT NOT NULL,
  `submission_time` DATETIME(6) DEFAULT NULL,
  `user_name` VARCHAR(255) DEFAULT NULL,
  `user_email` VARCHAR(255) DEFAULT NULL,
  `user_id` BIGINT NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_attendance_user_id` (`user_id`),
  KEY `idx_attendance_submission_time` (`submission_time`),
  CONSTRAINT `fk_attendance_user` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `fellowship_join_requests` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `fellowship_id` BIGINT NOT NULL,
  `user_name` VARCHAR(255) DEFAULT NULL,
  `user_email` VARCHAR(255) DEFAULT NULL,
  `user_phone` VARCHAR(255) DEFAULT NULL,
  `user_address` VARCHAR(255) DEFAULT NULL,
  `message` VARCHAR(255) DEFAULT NULL,
  `shared_with_emails` VARCHAR(255) DEFAULT NULL,
  `status` VARCHAR(255) DEFAULT NULL,
  `email_sent` BIT(1) NOT NULL DEFAULT b'0',
  `request_date` DATETIME(6) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_join_requests_fellowship_id` (`fellowship_id`),
  KEY `idx_join_requests_status` (`status`),
  CONSTRAINT `fk_join_requests_fellowship` FOREIGN KEY (`fellowship_id`) REFERENCES `fellowships` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `foundation_enrollments` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) DEFAULT NULL,
  `email` VARCHAR(255) DEFAULT NULL,
  `phone` VARCHAR(255) DEFAULT NULL,
  `status` VARCHAR(255) DEFAULT NULL,
  `email_sent` BIT(1) NOT NULL DEFAULT b'0',
  `shared_with_emails` VARCHAR(255) DEFAULT NULL,
  `request_date` DATETIME(6) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_foundation_enrollments_status` (`status`),
  KEY `idx_foundation_enrollments_email` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
