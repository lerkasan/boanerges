package net.lerkasan.capstone.service;

import jakarta.transaction.Transactional;
import net.lerkasan.capstone.model.Answer;

import java.util.List;

public interface AnswerServiceI {

    String NULL_ANSWER_ERROR = "Answer is null";

    String ANSWER_NOT_FOUND = "An answer with the id %d not found%n for the question with the id %d";

    @Transactional
    Answer create(Answer answer);

    Answer findByIdAndQuestionId(Long answerId, Long questionId);

    List<Answer> findByQuestionId(Long questionId);
}
