package net.lerkasan.capstone.service;

import net.lerkasan.capstone.dto.FeedbackDto;
import net.lerkasan.capstone.model.Answer;
import net.lerkasan.capstone.model.Feedback;

public interface FeedbackServiceI {
    String NULL_FEEDBACK_ERROR = "Feedback is null";

    Feedback create(final Feedback feedback);

    FeedbackDto generateFeedback(Answer answer);
}
