package net.lerkasan.capstone.service;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import net.lerkasan.capstone.exception.NotFoundException;
import net.lerkasan.capstone.model.Interview;
import net.lerkasan.capstone.repository.InterviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

import static net.lerkasan.capstone.service.UserServiceI.INVALID_USER_ID;

@Slf4j
@Service
public class InterviewService implements InterviewServiceI {

    private final InterviewRepository interviewRepo;

    @Autowired
    public InterviewService(InterviewRepository interviewRepo) {
        this.interviewRepo = interviewRepo;
    }

    @Override
    @Transactional
    public Interview create(final Interview interview) {
        Objects.requireNonNull(interview, NULL_INTERVIEW_ERROR);
        return interviewRepo.saveAndFlush(interview);
    }

    @Override
    @Transactional
    public Interview update(final Interview interview) {
        Objects.requireNonNull(interview, NULL_INTERVIEW_ERROR);
        return interviewRepo.saveAndFlush(interview);
    }

    @Override
    @Transactional
    public void delete(final long id) {
        if (id <= 0) {
            log.error(String.format(INVALID_INTERVIEW_ID_ERROR, id));
            throw new IllegalArgumentException(String.format(INVALID_INTERVIEW_ID_ERROR, id));
        }
        interviewRepo.deleteById(id);
    }

    @Override
    @Transactional
    public void delete(Interview interview) {
        interviewRepo.delete(interview);
    }

    @Override
    public List<Interview> findByUserId(long id) {
        if (id <= 0) {
            log.error(String.format(INVALID_USER_ID, id));
            throw new IllegalArgumentException(String.format(INVALID_INTERVIEW_ID_ERROR, id));
        }
        return interviewRepo.findByUserId(id);
    }

    @Override
    public Interview findByIdAndUserId(long id, long userId) {
        if (id <= 0) {
            log.error(String.format(INVALID_INTERVIEW_ID_ERROR, id));
            throw new IllegalArgumentException(String.format(INVALID_INTERVIEW_ID_ERROR, id));
        }
        if (userId <= 0) {
            log.error(String.format(INVALID_USER_ID, id));
            throw new IllegalArgumentException(String.format(INVALID_USER_ID, id));
        }
        return interviewRepo.findByIdAndUserId(id, userId).orElseThrow(() -> new NotFoundException(String.format(INTERVIEW_NOT_FOUND, id)));
    }

    public List<Interview> findAllByUserId(long id) {
        if (id <= 0) {
            log.error(String.format(INVALID_USER_ID, id));
            throw new IllegalArgumentException(String.format(INVALID_USER_ID, id));
        }
        return interviewRepo.findByUserId(id);
    }
}
