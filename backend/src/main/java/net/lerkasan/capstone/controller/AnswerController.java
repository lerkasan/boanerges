package net.lerkasan.capstone.controller;

import jakarta.validation.Valid;
import net.lerkasan.capstone.dto.AnswerDto;
import net.lerkasan.capstone.dto.QuestionDto;
import net.lerkasan.capstone.exception.NotFoundException;
import net.lerkasan.capstone.mapper.AnswerDtoMapper;
import net.lerkasan.capstone.model.Answer;
import net.lerkasan.capstone.model.Interview;
import net.lerkasan.capstone.model.Question;
import net.lerkasan.capstone.model.User;
import net.lerkasan.capstone.service.AnswerServiceI;
import net.lerkasan.capstone.service.InterviewServiceI;
import net.lerkasan.capstone.service.QuestionServiceI;
import net.lerkasan.capstone.service.UserServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/interviews/{interviewId}/questions/{questionId}/answers")
public class AnswerController {

    private final InterviewServiceI interviewService;

    private final QuestionServiceI questionService;

    private final AnswerServiceI answerService;

    private final UserServiceI userService;

    private final AnswerDtoMapper answerDtoMapper;

    @Autowired
    public AnswerController(InterviewServiceI interviewService, QuestionServiceI questionService, AnswerServiceI answerService, UserServiceI userService, AnswerDtoMapper answerDtoMapper) {
        this.interviewService = interviewService;
        this.questionService = questionService;
        this.answerService = answerService;
        this.userService = userService;
        this.answerDtoMapper = answerDtoMapper;
    }

    @PostMapping
    public ResponseEntity<AnswerDto> saveAnswer(@Valid @RequestBody AnswerDto answerDto, @PathVariable Long interviewId, @PathVariable Long questionId) {
        User user = userService.getCurrentUser();
        Interview interview = interviewService.findByIdAndUserId(interviewId, user.getId());
        Question question = questionService.findByIdAndInterviewId(questionId, interviewId);
        Answer answer = answerDtoMapper.toAnswer(answerDto);
        answer.setQuestion(question);
        Answer createdAnswer = answerService.create(answer);
        AnswerDto createdAnswerDto = answerDtoMapper.toAnswerDto(createdAnswer);

        final URI location = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(createdAnswer.getId())
                .toUri();
        return ResponseEntity.created(location).body(createdAnswerDto);
    }

    @DeleteMapping("/{id}")
    public void deleteAnswer(@PathVariable("id") Long id) {
        answerService.findById(id);
    }
}
