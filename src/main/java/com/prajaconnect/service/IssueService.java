package com.prajaconnect.service;

import com.prajaconnect.dto.IssueRequest;
import com.prajaconnect.model.Issue;
import com.prajaconnect.model.User;
import com.prajaconnect.repository.IssueRepository;
import com.prajaconnect.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IssueService {

    @Autowired
    private IssueRepository issueRepository;

    @Autowired
    private UserRepository userRepository;

    public Issue createIssue(IssueRequest request, String userEmail) {
        User author = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Issue issue = new Issue();
        issue.setTitle(request.getTitle());
        issue.setDescription(request.getDescription());
        issue.setCategory(request.getCategory());
        issue.setImage(request.getImage());
        issue.setAuthor(author);
        issue.setStatus("OPEN");
        return issueRepository.save(issue);
    }

    public List<Issue> getAllIssues() {
        return issueRepository.findAllByOrderByCreatedAtDesc();
    }

    public List<Issue> getIssuesByAuthor(Long authorId) {
        return issueRepository.findByAuthorId(authorId);
    }

    public List<Issue> getIssuesByStatus(String status) {
        return issueRepository.findByStatus(status);
    }

    public Issue updateIssueStatus(Long issueId, String newStatus) {
        Optional<Issue> issueOpt = issueRepository.findById(issueId);
        if (issueOpt.isPresent()) {
            Issue issue = issueOpt.get();
            issue.setStatus(newStatus);
            return issueRepository.save(issue);
        }
        throw new RuntimeException("Issue not found");
    }
    
    public void deleteIssue(Long issueId) {
        issueRepository.deleteById(issueId);
    }
}
