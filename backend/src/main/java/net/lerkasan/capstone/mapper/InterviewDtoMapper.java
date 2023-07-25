package net.lerkasan.capstone.mapper;

import net.lerkasan.capstone.dto.InterviewDto;
import net.lerkasan.capstone.model.Interview;
import net.lerkasan.capstone.service.TopicServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class InterviewDtoMapper {

    private final TopicServiceI topicService;

    @Autowired
    public InterviewDtoMapper(TopicServiceI topicService) {
        this.topicService = topicService;
    }

    public InterviewDto toInterviewDto(Interview interview) {
        return new InterviewDto(
                interview.getId(),
                interview.getName(),
                interview.getTopic().getId(),
                interview.getQuestions());
    }

    public Interview toInterview(InterviewDto interviewDto) {
        return new Interview(
                interviewDto.getId(),
                interviewDto.getName(),
                topicService.findById(interviewDto.getTopicId()),
                interviewDto.getQuestions()
        );
    }
}