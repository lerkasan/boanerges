package net.lerkasan.capstone.repository;

import net.lerkasan.capstone.model.Interview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InterviewRepository extends JpaRepository<Interview, Long> {

    final String AVERAGE_SCORE_QUERY = "SELECT avg(f.score) FROM Interview i LEFT JOIN i.questions q LEFT JOIN q.answer a LEFT JOIN a.feedback f WHERE i.id = :id";

//    @Query("SELECT avg(f.score) FROM Interview i LEFT JOIN Question q ON i.id = q.interview_id LEFT JOIN Answer a ON q.id = a.question_id LEFT JOIN Feedback f ON a.id = f.answer_id WHERE i.id = :id")
//    double calculateAverageScore(@Param("id") Long id );

//    SELECT avg(f.score)
//    FROM interviews i LEFT JOIN questions q ON i.id = q.interview_id
//    LEFT JOIN answers a ON q.id = a.question_id
//    LEFT JOIN feedbacks f ON a.id = f.answer_id
//    WHERE i.id = :id;

    @Query(AVERAGE_SCORE_QUERY)
    double calculateAverageScore(@Param("id") long id );

    Optional<Interview> findByIdAndUserId(@Param("id") long id, @Param("userId") long userId);
//
//    Optional<Interview> findByIdAndUsername(@Param("id") long id, @Param("username") String username);

    List<Interview> findByUserId(long id);
}