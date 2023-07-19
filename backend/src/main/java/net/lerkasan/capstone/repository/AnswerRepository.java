package net.lerkasan.capstone.repository;

import net.lerkasan.capstone.model.Answer;
import net.lerkasan.capstone.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {

    Optional<Answer> findByQuestion(final Question question);

    Optional<Answer> findByQuestionId(final Long questionId);
}