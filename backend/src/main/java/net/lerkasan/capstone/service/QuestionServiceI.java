package net.lerkasan.capstone.service;

import jakarta.transaction.Transactional;
import net.lerkasan.capstone.dto.QuestionDto;
import net.lerkasan.capstone.model.Question;
import net.lerkasan.capstone.model.Topic;

import java.util.List;

public interface QuestionServiceI {

    String NULL_QUESTION_ERROR = "Question is null";

    String INVALID_QUESTION_ID_ERROR = "Question id must be greater than 0";

    String QUESTION_NOT_FOUND = "A question with the id %d not found%n in the interview with the id %d";

    Question create(final Question question);

    @Transactional
    Question update(Question question);

    @Transactional
    void delete(long id);

    @Transactional
    void delete(Question question);

    QuestionDto generateQuestion(Topic topic);

    Question findByIdAndInterviewId(Long questionId, Long interviewId);

    List<Question> getQuestions();
}
