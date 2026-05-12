package com.christembassy.pune;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.Optional;
@RestController
@RequestMapping("/api/attendance")
// REMOVED @CrossOrigin because WebConfig handles it.
public class AttendanceController {
    @Autowired
    private AttendanceRepository attendanceRepository;
    @Autowired
    private UserRepository userRepository;
    @PostMapping("/submit")
    public ResponseEntity<?> submitAttendance(@RequestBody Map<String, Object> payload) {
        try {
            // payload: { "userId": 1, "count": 2 }
            Object userIdObj = payload.get("userId");
            Long userId = null;
            if (userIdObj instanceof Integer) {
                userId = ((Integer) userIdObj).longValue();
            } else if (userIdObj instanceof Long) {
                userId = (Long) userIdObj;
            } else if (userIdObj instanceof String) {
                userId = Long.parseLong((String) userIdObj);
            }
            if (userId == null) return ResponseEntity.badRequest().body("User ID is required");

            int count = Integer.parseInt(payload.get("count").toString());
            // Get the user from the SecurityContext (set by JwtFilter)
            String loginIdentifier = org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication().getName();
            Optional<User> userOptional = userRepository.findByLoginIdentifier(loginIdentifier);
            
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                Attendance attendance = new Attendance(user, count);
                attendanceRepository.save(attendance);
                return ResponseEntity.ok("Attendance submitted successfully");
            } else {
                return ResponseEntity.badRequest().body("User not found or not authenticated");
            }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }
}
