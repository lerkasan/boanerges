package net.lerkasan.capstone.repository;

import net.lerkasan.capstone.model.Answer;
import net.lerkasan.capstone.model.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {

    Optional<Feedback> findByAnswer(final Answer answer);

    Optional<Feedback> findByAnswerId(final Long answerId);
}
