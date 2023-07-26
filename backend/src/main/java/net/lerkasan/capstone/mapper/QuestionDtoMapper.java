package net.lerkasan.capstone.mapper;

import net.lerkasan.capstone.dto.QuestionDto;
import net.lerkasan.capstone.model.Question;
import net.lerkasan.capstone.model.User;
import net.lerkasan.capstone.service.InterviewServiceI;
import net.lerkasan.capstone.service.UserServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class QuestionDtoMapper {

//    private final InterviewServiceI interviewService;
//    private final UserServiceI userService;

//    @Autowired
//    public QuestionDtoMapper(InterviewServiceI interviewService, UserServiceI userService) {
//        this.interviewService = interviewService;
//        this.userService = userService;
//    }

    public QuestionDto toQuestionDto(Question question) {
        return new QuestionDto(
                question.getId(),
                question.getText(),
                question.getAudioUrl());
    }

    public Question toQuestion(QuestionDto questionDto) {
//        User user = userService.getCurrentUser();
        return new Question(
                questionDto.getId(),
                questionDto.getText(),
                questionDto.getAudioUrl()
//                interviewService.findByIdAndUserId(questionDto.getInterviewId(), user.getId())
        );
    }
}