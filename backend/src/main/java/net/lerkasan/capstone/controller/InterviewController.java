package net.lerkasan.capstone.controller;

import net.lerkasan.capstone.model.Interview;
import net.lerkasan.capstone.model.User;
import net.lerkasan.capstone.service.InterviewService;
import net.lerkasan.capstone.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/interview")
public class InterviewController {

    private final InterviewService interviewService;
    private final UserService userService;

    @Autowired
    public InterviewController(InterviewService interviewService, UserService userService) {
        this.interviewService = interviewService;
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public Interview getInterview(@PathVariable long id, @AuthenticationPrincipal UserDetails currentUser) {
//        return interviewService.findByIdAndUsername(id, currentUser.getUsername());
        User user = userService.findByUsername(currentUser.getUsername());
        return interviewService.findByIdAndUserId(id, user.getId());
    }

    @PostMapping
    public Interview createInterview(UserDetails currentUser) {
        return interviewService.create(new Interview(currentUser.getUsername()));
    }
}