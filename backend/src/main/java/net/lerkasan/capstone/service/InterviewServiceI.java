package net.lerkasan.capstone.service;

import jakarta.transaction.Transactional;
import net.lerkasan.capstone.model.Interview;

import java.util.List;
import java.util.Objects;

public interface InterviewServiceI {
    String INTERVIEW_NOT_FOUND = "An interview with the id %d not found%n";
    String NULL_INTERVIEW_ERROR = "Interview is null";

    String INVALID_INTERVIEW_ID_ERROR = "Interview id must be greater than 0";

    Interview findByIdAndUserId(long id, long userId);

//    Interview findByIdAndUsername(long id, String username);

    List<Interview> findAllByUserId(long id);

    Interview create(Interview interview);

    Interview update(final Interview interview);

    void delete(final long id);

    void delete(Interview interview);

    List<Interview> findByUserId(long id);
}
