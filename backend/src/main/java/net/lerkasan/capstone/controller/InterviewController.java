package net.lerkasan.capstone.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import net.lerkasan.capstone.dto.InterviewDto;
import net.lerkasan.capstone.mapper.InterviewDtoMapper;
import net.lerkasan.capstone.model.Interview;
import net.lerkasan.capstone.model.User;
import net.lerkasan.capstone.service.InterviewServiceI;
import net.lerkasan.capstone.service.UserServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping(InterviewController.INTERVIEWS_ENDPOINT)
public class InterviewController {

    public static final String INTERVIEWS_ENDPOINT = "/api/v1/interviews";
    public static final String ID = "/{id}";
    public static final String CREATING_INTERVIEW = "Creating interview: {}";
    public static final String GETTING_INTERVIEW_WITH_ID_FOR_USER = "Getting interview with id: {} for user: {}";
    private final InterviewDtoMapper interviewDtoMapper;
    private final InterviewServiceI interviewService;
    private final UserServiceI userService;

    @Autowired
    public InterviewController(InterviewDtoMapper interviewDtoMapper, InterviewServiceI interviewService, UserServiceI userService) {
        this.interviewDtoMapper = interviewDtoMapper;
        this.interviewService = interviewService;
        this.userService = userService;
    }

    @GetMapping(ID)
    public InterviewDto getInterview(@PathVariable Long id, @AuthenticationPrincipal UserDetails currentUser) {
        User user = userService.findByUsername(currentUser.getUsername());
        log.info(GETTING_INTERVIEW_WITH_ID_FOR_USER, id, user);
        Interview interview = interviewService.findByIdAndUserId(id, user.getId());
        return interviewDtoMapper.toInterviewDto(interview);
    }

    @GetMapping
    public List<InterviewDto> getInterviews(@AuthenticationPrincipal UserDetails currentUser) {
        User user = userService.findByUsername(currentUser.getUsername());
        return interviewService.findByUserId(user.getId()).stream().map(interviewDtoMapper::toInterviewDto).collect(Collectors.toList());
    }

    @PostMapping
    public ResponseEntity<InterviewDto> createInterview(@Valid @RequestBody InterviewDto interviewDto, Authentication authentication) {
        User currentUser = userService.findByUsername(authentication.getName());
        Interview interview = interviewDtoMapper.toInterview(interviewDto);
        interview.setUser(currentUser);
        log.info(CREATING_INTERVIEW, interview);
        Interview createdInterview = interviewService.create(interview);
        InterviewDto createdInterviewDto = interviewDtoMapper.toInterviewDto(createdInterview);

        final URI location = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path(ID)
                .buildAndExpand(createdInterview.getId())
                .toUri();
        return ResponseEntity.created(location).body(createdInterviewDto);
    }

    @PutMapping(ID)
    public void renameInterview(@PathVariable Long id, @RequestParam String name, @AuthenticationPrincipal UserDetails currentUser) {
        User user = userService.findByUsername(currentUser.getUsername());
        Interview interview = interviewService.findByIdAndUserId(id, user.getId());
        interview.setName(name);
        interviewService.update(interview);
    }

    @DeleteMapping(ID)
    public void deleteInterview(@PathVariable Long id, @AuthenticationPrincipal UserDetails currentUser) {
        User user = userService.findByUsername(currentUser.getUsername());
        Interview interview = interviewService.findByIdAndUserId(id, user.getId());
        interviewService.delete(interview);
    }
}