package net.lerkasan.capstone.service;

import jakarta.transaction.Transactional;
import net.lerkasan.capstone.model.Question;
import net.lerkasan.capstone.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class QuestionService implements QuestionServiceI {

    private final QuestionRepository questionRepo;

    @Autowired
    public QuestionService(QuestionRepository questionRepo) {
        this.questionRepo = questionRepo;
    }

    @Override
    @Transactional
    public Question create(final Question question) {
        Objects.requireNonNull(question, NULL_QUESTION_ERROR);
        return questionRepo.saveAndFlush(question);
    }
}
