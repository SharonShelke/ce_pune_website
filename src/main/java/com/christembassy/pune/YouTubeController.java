package com.christembassy.pune;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/youtube")
public class YouTubeController {

    @Autowired
    private YouTubeService youTubeService;

    @Value("${admin.live.secret:cepune-admin-2026}")
    private String adminSecret;

    @GetMapping("/latest")
    public String getLatestVideo() throws Exception {
        return youTubeService.getLatestVideo();
    }

    @GetMapping("/status")
    public Map<String, Object> getStatus() {
        return youTubeService.getStatus();
    }

    /**
     * Force a specific video ID to show as "LIVE NOW" on the website.
     * Use this when going live on an unlisted YouTube stream.
     * Example: POST /api/youtube/force-live?videoId=abc123&secret=cepune-admin-2026
     */
    @PostMapping("/force-live")
    public ResponseEntity<Map<String, Object>> forceLive(
            @RequestParam String videoId,
            @RequestParam String secret) {

        Map<String, Object> response = new HashMap<>();
        if (!adminSecret.equals(secret)) {
            response.put("success", false);
            response.put("message", "Invalid secret key.");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        }

        youTubeService.forceLiveStatus(videoId);
        response.put("success", true);
        response.put("message", "Live stream override activated.");
        response.put("videoId", videoId);
        return ResponseEntity.ok(response);
    }

    /**
     * Clear the manual override and revert to automatic YouTube API detection.
     * Example: POST /api/youtube/clear-live?secret=cepune-admin-2026
     */
    @PostMapping("/clear-live")
    public ResponseEntity<Map<String, Object>> clearLive(@RequestParam String secret) {

        Map<String, Object> response = new HashMap<>();
        if (!adminSecret.equals(secret)) {
            response.put("success", false);
            response.put("message", "Invalid secret key.");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        }

        youTubeService.clearForceLiveStatus();
        response.put("success", true);
        response.put("message", "Live stream override cleared. Reverting to automatic detection.");
        return ResponseEntity.ok(response);
    }

    /**
     * Get the current admin state (is override active? what videoId?).
     * Example: GET /api/youtube/admin-status?secret=cepune-admin-2026
     */
    @GetMapping("/admin-status")
    public ResponseEntity<Map<String, Object>> getAdminStatus(@RequestParam String secret) {
        Map<String, Object> response = new HashMap<>();
        if (!adminSecret.equals(secret)) {
            response.put("success", false);
            response.put("message", "Invalid secret key.");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        }

        Map<String, Object> currentStatus = youTubeService.getStatus();
        response.put("forceLiveActive", youTubeService.isForceLiveActive());
        response.put("currentStatus", currentStatus);
        return ResponseEntity.ok(response);
    }
}
