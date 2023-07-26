package net.lerkasan.capstone.service;

import net.lerkasan.capstone.model.Question;

public interface QuestionServiceI {

    String NULL_QUESTION_ERROR = "Question is null";

    Question create(final Question question);
}
