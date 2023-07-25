package net.lerkasan.capstone.controller;

import net.lerkasan.capstone.dto.InterviewDto;
import net.lerkasan.capstone.model.Interview;
import net.lerkasan.capstone.model.User;
import net.lerkasan.capstone.service.InterviewServiceI;
import net.lerkasan.capstone.service.UserServiceI;
import net.lerkasan.capstone.mapper.InterviewDtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/interviews")
public class InterviewController {

    private final InterviewDtoMapper interviewDtoMapper;
    private final InterviewServiceI interviewService;
    private final UserServiceI userService;

    @Autowired
    public InterviewController(InterviewDtoMapper interviewDtoMapper, InterviewServiceI interviewService, UserServiceI userService) {
        this.interviewDtoMapper = interviewDtoMapper;
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
    public ResponseEntity<Interview> createInterview(@RequestBody InterviewDto interviewDto, Authentication authentication) {
//    public ResponseEntity<Interview> createInterview(@Valid @RequestBody Interview interview, Authentication authentication) {
        User currentUser = userService.findByUsername(authentication.getName());
        Interview interview = interviewDtoMapper.toInterview(interviewDto);
        interview.setUser(currentUser);
        Interview createdInterview = interviewService.create(interview);

        final URI location = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(createdInterview.getId())
                .toUri();
        return ResponseEntity.created(location).body(createdInterview);
    }
}