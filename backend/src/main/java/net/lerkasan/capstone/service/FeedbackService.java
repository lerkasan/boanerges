package net.lerkasan.capstone.service;

import net.lerkasan.capstone.model.Feedback;
import net.lerkasan.capstone.repository.FeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class FeedbackService implements FeedbackServiceI {

    private final FeedbackRepository feedbackRepo;

    @Autowired
    public FeedbackService(FeedbackRepository feedbackRepo) {
        this.feedbackRepo = feedbackRepo;
    }

    @Override
    public Feedback create(Feedback feedback) {
        Objects.requireNonNull(feedback, NULL_FEEDBACK_ERROR);
        return feedbackRepo.saveAndFlush(feedback);
    }
}
