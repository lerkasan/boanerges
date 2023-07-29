package net.lerkasan.capstone.repository;

import net.lerkasan.capstone.model.Category;
import net.lerkasan.capstone.model.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Long> {

    List<Topic> findByCategory(final Category category);

    List<Topic> findByCategoryId(final Long categoryId);
}
