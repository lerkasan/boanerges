package net.lerkasan.capstone.service;

import jakarta.transaction.Transactional;
import net.lerkasan.capstone.model.Answer;

public interface AnswerServiceI {

    String NULL_ANSWER_ERROR = "Answer is null";

    @Transactional
    Answer create(Answer answer);
}
