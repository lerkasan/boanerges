package net.lerkasan.capstone.controller;

import net.lerkasan.capstone.dto.FeedbackDto;
import net.lerkasan.capstone.model.*;
import net.lerkasan.capstone.service.AnswerServiceI;
import net.lerkasan.capstone.service.InterviewServiceI;
import net.lerkasan.capstone.service.QuestionServiceI;
import net.lerkasan.capstone.service.UserServiceI;
import net.lerkasan.capstone.service.FeedbackServiceI;
import net.lerkasan.capstone.mapper.FeedbackDtoMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(FeedbackController.FEEDBACK_ENDPOINT)
public class FeedbackController {

    public static final String ID = "/{id}";
    public static final String FEEDBACK_ENDPOINT = "/api/v1/interviews/{interviewId}/questions/{questionId}/answers/{answerId}/feedback";
    private final UserServiceI userService;
    private final InterviewServiceI interviewService;
    private final QuestionServiceI questionService;
    private final AnswerServiceI answerService;
    private final FeedbackServiceI feedbackService;
    private final FeedbackDtoMapper feedbackDtoMapper;

    public FeedbackController(UserServiceI userService, InterviewServiceI interviewService, QuestionServiceI questionService, AnswerServiceI answerService, FeedbackServiceI feedbackService, FeedbackDtoMapper feedbackDtoMapper) {
        this.userService = userService;
        this.interviewService = interviewService;
        this.questionService = questionService;
        this.answerService = answerService;
        this.feedbackService = feedbackService;
        this.feedbackDtoMapper = feedbackDtoMapper;
    }

    @PostMapping
    public ResponseEntity<FeedbackDto> generateFeedback(@PathVariable Long interviewId, @PathVariable Long questionId, @PathVariable Long answerId) {
        User user = userService.getCurrentUser();
        Interview interview = interviewService.findByIdAndUserId(interviewId, user.getId());
        Question question = questionService.findByIdAndInterviewId(questionId, interviewId);
        Answer answer = answerService.findByIdAndQuestionId(answerId, questionId);

        FeedbackDto feedbackDto = feedbackService.generateFeedback(answer);
        Feedback feedback = feedbackDtoMapper.toFeedback(feedbackDto);
        feedback.setAnswer(answer);
        Feedback createdFeedback = feedbackService.create(feedback);
        FeedbackDto createdFeedbackDto = feedbackDtoMapper.toFeedbackDto(createdFeedback);

        final URI location = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path(ID)
                .buildAndExpand(createdFeedback.getId())
                .toUri();
        return ResponseEntity.created(location).body(createdFeedbackDto);

    }
}
