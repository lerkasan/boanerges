package net.lerkasan.capstone.service;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import net.lerkasan.capstone.exception.NotFoundException;
import net.lerkasan.capstone.model.Answer;
import net.lerkasan.capstone.repository.AnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

import static net.lerkasan.capstone.service.QuestionServiceI.INVALID_QUESTION_ID_ERROR;

@Slf4j
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
    @Transactional
    public Answer update(final Answer answer) {
        Objects.requireNonNull(answer, NULL_ANSWER_ERROR);
        return answerRepo.saveAndFlush(answer);
    }

    @Override
    @Transactional
    public void delete(final long id) {
        if (id <= 0) {
            log.error(String.format(INVALID_ANSWER_ID_ERROR, id));
            throw new IllegalArgumentException(String.format(INVALID_ANSWER_ID_ERROR, id));
        }
        answerRepo.deleteById(id);
    }

    @Override
    @Transactional
    public void delete(Answer answer) {
        answerRepo.delete(answer);
    }

    @Override
    public Answer findByIdAndQuestionId(Long answerId, Long questionId) {
        if (questionId <= 0) {
            log.error(String.format(INVALID_QUESTION_ID_ERROR, questionId));
            throw new IllegalArgumentException(String.format(INVALID_QUESTION_ID_ERROR, questionId));
        }
        if (answerId <= 0) {
            log.error(String.format(INVALID_ANSWER_ID_ERROR, questionId));
            throw new IllegalArgumentException(String.format(INVALID_ANSWER_ID_ERROR, questionId));
        }
        return answerRepo.findByIdAndQuestionId(answerId, questionId).orElseThrow(() -> new NotFoundException(String.format(ANSWER_NOT_FOUND, answerId, questionId)));
    }

    @Override
    public List<Answer> findByQuestionId(Long questionId) {
        if (questionId <= 0) {
            log.error(String.format(INVALID_QUESTION_ID_ERROR, questionId));
            throw new IllegalArgumentException(String.format(INVALID_QUESTION_ID_ERROR, questionId));
        }
        return answerRepo.findByQuestionId(questionId);
    }

    @Override
    public Answer findById(Long id) {
        if (id <= 0) {
            log.error(String.format(INVALID_ANSWER_ID_ERROR, id));
            throw new IllegalArgumentException(String.format(INVALID_ANSWER_ID_ERROR, id));
        }
        return answerRepo.findById(id).orElseThrow(() -> new NotFoundException(String.format(ANSWER_NOT_FOUND_BY_ID, id)));
    }
}
