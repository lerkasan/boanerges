package net.lerkasan.capstone.service;

import jakarta.transaction.Transactional;
import net.lerkasan.capstone.dto.FeedbackDto;
import net.lerkasan.capstone.model.Answer;
import net.lerkasan.capstone.model.Feedback;

public interface FeedbackServiceI {
    String NULL_FEEDBACK_ERROR = "Feedback is null";

    String FEEDBACK_NOT_FOUND = "A feedback with the id %d not found.";

    String INVALID_FEEDBACK_ID_ERROR = "Feedback id must be greater than 0";

    Feedback create(final Feedback feedback);

    Feedback update(Feedback feedback);

    void findById(long id);

    @Transactional
    void delete(long id);

    @Transactional
    void delete(Feedback feedback);

    FeedbackDto generateFeedback(Answer answer);
}
