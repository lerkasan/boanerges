package net.lerkasan.capstone.mapper;

import net.lerkasan.capstone.dto.InterviewDto;
import net.lerkasan.capstone.dto.QuestionDto;
import net.lerkasan.capstone.model.Interview;
import net.lerkasan.capstone.model.Question;
import net.lerkasan.capstone.service.TopicServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class InterviewDtoMapper {

    private final TopicServiceI topicService;

    private final QuestionDtoMapper questionDtoMapper;

    @Autowired
    public InterviewDtoMapper(TopicServiceI topicService, QuestionDtoMapper questionDtoMapper) {
        this.topicService = topicService;
        this.questionDtoMapper = questionDtoMapper;
    }

    public InterviewDto toInterviewDto(Interview interview) {
        return new InterviewDto(
                interview.getId(),
                interview.getName(),
                interview.getTopic().getId(),
                toQuestionDtoSet(interview.getQuestions()));
    }

    public Interview toInterview(InterviewDto interviewDto) {
        return new Interview(
                interviewDto.getId(),
                interviewDto.getName(),
                topicService.findById(interviewDto.getTopicId()),
                Set.of());
    }

    public Set<QuestionDto> toQuestionDtoSet(Set<Question> questions) {
        return questions.stream().map(questionDtoMapper::toQuestionDto).collect(Collectors.toSet());
    }
}