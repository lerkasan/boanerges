package net.lerkasan.capstone.service;

import jakarta.transaction.Transactional;
import net.lerkasan.capstone.exception.NotFoundException;
import net.lerkasan.capstone.model.Answer;
import net.lerkasan.capstone.repository.AnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class AnswerService implements AnswerServiceI {

    private final AnswerRepository answerRepo;

    @Autowired
    public AnswerService(AnswerRepository answerRepo) {
        this.answerRepo = answerRepo;
    }

    @Override
    @Transactional
    public Answer create(final Answer answer) {
        Objects.requireNonNull(answer, NULL_ANSWER_ERROR);
        return answerRepo.saveAndFlush(answer);
    }

    @Override
    public Answer findByIdAndQuestionId(Long answerId, Long questionId) {
        return answerRepo.findByIdAndQuestionId(answerId, questionId).orElseThrow(() -> new NotFoundException(String.format(ANSWER_NOT_FOUND, answerId, questionId)));
    }
}
