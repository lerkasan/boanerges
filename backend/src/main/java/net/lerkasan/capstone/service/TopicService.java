package net.lerkasan.capstone.service;

import net.lerkasan.capstone.exception.NotFoundException;
import net.lerkasan.capstone.model.Book;
import net.lerkasan.capstone.model.Topic;
import net.lerkasan.capstone.repository.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TopicService implements TopicServiceI {

    String TOPIC_NOT_FOUND_BY_ID = "A topic with the id %d not found%n";

    private final TopicRepository topicRepository;

    @Autowired
    public TopicService(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }

    public Topic findById(long id) {
        return topicRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format(TOPIC_NOT_FOUND_BY_ID, id)));
    }
}
