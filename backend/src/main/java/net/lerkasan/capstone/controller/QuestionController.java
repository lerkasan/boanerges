package net.lerkasan.capstone.controller;

import jakarta.validation.Valid;
import net.lerkasan.capstone.dto.QuestionDto;
import net.lerkasan.capstone.mapper.QuestionDtoMapper;
import net.lerkasan.capstone.model.Interview;
import net.lerkasan.capstone.model.Question;
import net.lerkasan.capstone.model.Topic;
import net.lerkasan.capstone.model.User;
import net.lerkasan.capstone.service.InterviewServiceI;
import net.lerkasan.capstone.service.QuestionServiceI;
import net.lerkasan.capstone.service.TopicServiceI;
import net.lerkasan.capstone.service.UserServiceI;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping
public class QuestionController {

    public static final String GENERATE_QUESTIONS_ENDPOINT = "/api/v1/questions";
    public static final String SAVE_QUESTIONS_ENDPOINT = "/api/v1/interviews/{interviewId}/questions";
    public static final String ID = "/{id}";
    private final TopicServiceI topicService;

    private final QuestionDtoMapper questionDtoMapper;

    private final QuestionServiceI questionService;

    private final InterviewServiceI interviewService;

    private final UserServiceI userService;

    public QuestionController(TopicServiceI topicService, QuestionDtoMapper questionDtoMapper, QuestionServiceI questionService, InterviewServiceI interviewService, UserServiceI userService) {
        this.topicService = topicService;
        this.questionDtoMapper = questionDtoMapper;
        this.questionService = questionService;
        this.interviewService = interviewService;
        this.userService = userService;
    }

    @PostMapping(GENERATE_QUESTIONS_ENDPOINT)
    public QuestionDto generateQuestion(@RequestParam Long topicId) {
        Topic topic = topicService.findById(topicId);
        return questionService.generateQuestion(topic);
    }

    @PostMapping(SAVE_QUESTIONS_ENDPOINT)
    public ResponseEntity<QuestionDto> saveQuestion(@PathVariable long interviewId, @Valid @RequestBody QuestionDto questionDto) {
        User currentUser = userService.getCurrentUser();
        Interview interview = interviewService.findByIdAndUserId(interviewId, currentUser.getId());
        Question question = questionDtoMapper.toQuestion(questionDto);
        question.setInterview(interview);
        Question createdQuestion = questionService.create(question);
        QuestionDto createdQuestionDto = questionDtoMapper.toQuestionDto(createdQuestion);

        final URI location = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path(ID)
                .buildAndExpand(createdQuestion.getId())
                .toUri();
        return ResponseEntity.created(location).body(createdQuestionDto);
    }

    @GetMapping(GENERATE_QUESTIONS_ENDPOINT)
    @ResponseStatus(HttpStatus.OK)
    public List<String> getQuestions() {
        return questionService.getQuestions().stream().map(Question::getText).toList();
    }
}
