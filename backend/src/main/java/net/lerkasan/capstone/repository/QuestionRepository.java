package net.lerkasan.capstone.repository;

import net.lerkasan.capstone.model.Answer;
import net.lerkasan.capstone.model.Interview;
import net.lerkasan.capstone.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

        List<Question> findByInterview(final Interview interview);

        List<Question> findByInterviewId(final Long interviewId);
}
