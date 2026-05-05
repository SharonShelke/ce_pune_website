package com.christembassy.pune;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/fellowships")
@CrossOrigin(origins = "*")
public class FellowshipController {

    @Autowired
    private FellowshipRepository fellowshipRepository;

    @Autowired
    private JoinRequestRepository joinRequestRepository;

    @Autowired
    private EmailService emailService;

    // 0. Get all fellowships
    @GetMapping
    public ResponseEntity<List<Fellowship>> getAllFellowships() {
        return ResponseEntity.ok(fellowshipRepository.findAll());
    }

    // 1. Submit join request
    @PostMapping("/{cellId}/join")
    public ResponseEntity<?> joinFellowship(@PathVariable Long cellId, @RequestBody EnrollmentRequest request) {
        Optional<Fellowship> fellowshipOpt = fellowshipRepository.findById(cellId);
        if (fellowshipOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Fellowship not found with ID: " + cellId);
        }

        Fellowship fellowship = fellowshipOpt.get();

        FellowshipJoinRequest joinRequest = new FellowshipJoinRequest();
        joinRequest.setFellowship(fellowship);
        joinRequest.setUserName(request.getName());
        joinRequest.setUserEmail(request.getEmail());
        joinRequest.setUserPhone(request.getPhone());
        joinRequest.setUserAddress(request.getAddress());
        joinRequest.setMessage(request.getMessage());
        joinRequest.setStatus("PENDING");
        
        String adminEmails = "7jbking0102@gmail.com, sharonshelke1@gmail.com";
        joinRequest.setSharedWithEmails(adminEmails);

        try {
            // Send email notification
            emailService.sendFellowshipJoinNotification(
                    fellowship.getName(),
                    request.getName(),
                    request.getEmail(),
                    request.getPhone(),
                    request.getAddress(), // Added address
                    request.getMessage()
            );
            joinRequest.setEmailSent(true);
        } catch (Exception e) {
            // Still save the request but mark email as not sent
            joinRequest.setEmailSent(false);
            System.err.println("Failed to send join request email: " + e.getMessage());
        }

        joinRequestRepository.save(joinRequest);
        return ResponseEntity.ok("Join request submitted successfully.");
    }

    // 2. List requests per cell (for leader)
    @GetMapping("/{cellId}/requests")
    public ResponseEntity<List<FellowshipJoinRequest>> getRequestsByCell(@PathVariable Long cellId) {
        List<FellowshipJoinRequest> requests = joinRequestRepository.findByFellowshipId(cellId);
        return ResponseEntity.ok(requests);
    }

    // 3. All pending requests (admin dashboard)
    @GetMapping("/requests/pending")
    public ResponseEntity<List<FellowshipJoinRequest>> getPendingRequests() {
        List<FellowshipJoinRequest> requests = joinRequestRepository.findByStatus("PENDING");
        return ResponseEntity.ok(requests);
    }

    // 4. Confirm or reject request
    @PatchMapping("/requests/{id}/status")
    public ResponseEntity<?> updateRequestStatus(@PathVariable Long id, @RequestBody Map<String, String> statusUpdate) {
        String newStatus = statusUpdate.get("status");
        if (newStatus == null || (!newStatus.equals("APPROVED") && !newStatus.equals("REJECTED"))) {
            return ResponseEntity.badRequest().body("Invalid status. Use APPROVED or REJECTED.");
        }

        Optional<FellowshipJoinRequest> requestOpt = joinRequestRepository.findById(id);
        if (requestOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        FellowshipJoinRequest joinRequest = requestOpt.get();
        joinRequest.setStatus(newStatus);
        joinRequestRepository.save(joinRequest);

        return ResponseEntity.ok("Request " + newStatus.toLowerCase() + " successfully.");
    }
}
