package net.lerkasan.capstone.service;

import jakarta.transaction.Transactional;
import net.lerkasan.capstone.model.Answer;

import java.util.List;

public interface AnswerServiceI {

    String NULL_ANSWER_ERROR = "Answer is null";

    String INVALID_ANSWER_ID_ERROR = "Answer id must be greater than 0";
    String ANSWER_NOT_FOUND = "An answer with the id %d not found%n for the question with the id %d";
    String ANSWER_NOT_FOUND_BY_ID = "An answer with the id %d not found.";

    @Transactional
    Answer create(Answer answer);

    @Transactional
    Answer update(Answer answer);

    @Transactional
    void delete(long id);

    @Transactional
    void delete(Answer answer);

    Answer findByIdAndQuestionId(Long answerId, Long questionId);

    List<Answer> findByQuestionId(Long questionId);

    Answer findById(Long id);
}
