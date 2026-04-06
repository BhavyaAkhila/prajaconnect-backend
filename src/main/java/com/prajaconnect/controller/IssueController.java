package com.prajaconnect.controller;

import com.prajaconnect.dto.IssueRequest;
import com.prajaconnect.dto.MessageResponse;
import com.prajaconnect.model.Issue;
import com.prajaconnect.service.IssueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/issues")
public class IssueController {

    @Autowired
    private IssueService issueService;

    @PostMapping
    public ResponseEntity<?> createIssue(@Valid @RequestBody IssueRequest issueRequest, Authentication authentication) {
        String email = authentication.getName();
        issueService.createIssue(issueRequest, email);
        return ResponseEntity.ok(new MessageResponse("Issue created successfully!"));
    }

    @GetMapping
    public ResponseEntity<List<Issue>> getAllIssues() {
        return ResponseEntity.ok(issueService.getAllIssues());
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<?> updateStatus(@PathVariable Long id, @RequestBody java.util.Map<String, String> body) {
        String status = body.get("status");
        issueService.updateIssueStatus(id, status);
        return ResponseEntity.ok(new MessageResponse("Issue status updated to " + status));
    }
}
