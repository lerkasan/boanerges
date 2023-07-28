package net.lerkasan.capstone.service;

import jakarta.transaction.Transactional;
import net.lerkasan.capstone.exception.NotFoundException;
import net.lerkasan.capstone.model.Interview;
import net.lerkasan.capstone.repository.InterviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

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
        interviewRepo.deleteById(id);
    }

    @Override
    @Transactional
    public void delete(Interview interview) {
        interviewRepo.delete(interview);
    }

    @Override
    public List<Interview> findByUserId(long id) {
        return interviewRepo.findByUserId(id);
    }

    @Override
    public Interview findByIdAndUserId(long id, long userId) {
        return interviewRepo.findByIdAndUserId(id, userId).orElseThrow(() -> new NotFoundException(String.format(INTERVIEW_NOT_FOUND, id)));
    }

//    @Override
//    public Interview findByIdAndUsername(long id, String username) {
//        return interviewRepo.findByIdAndUsername(id, username).orElseThrow(() -> new NotFoundException(String.format(INTERVIEW_NOT_FOUND, id)));
//    }

    public List<Interview> findAllByUserId(long id) {
        return interviewRepo.findByUserId(id);
    }
}
