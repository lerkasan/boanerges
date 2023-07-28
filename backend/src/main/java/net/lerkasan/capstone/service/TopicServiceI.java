package net.lerkasan.capstone.service;

import jakarta.transaction.Transactional;
import net.lerkasan.capstone.model.Topic;

import java.util.List;

public interface TopicServiceI {

    String TOPIC_NOT_FOUND_BY_ID = "A topic with the id %d not found%n";
    String NULL_TOPIC_ERROR = "Topic is null";

    String INVALID_TOPIC_ID_ERROR = "Topic id must be greater than 0";

    @Transactional
    Topic create(Topic topic);

    @Transactional
    Topic update(Topic topic);

    @Transactional
    void delete(long id);

    @Transactional
    void delete(Topic topic);

    Topic findById(long id);

    List<Topic> getTopics();

}
