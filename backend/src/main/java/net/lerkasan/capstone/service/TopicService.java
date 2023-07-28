package net.lerkasan.capstone.service;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import net.lerkasan.capstone.exception.NotFoundException;
import net.lerkasan.capstone.model.Topic;
import net.lerkasan.capstone.repository.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class TopicService implements TopicServiceI {

    private final TopicRepository topicRepository;

    @Autowired
    public TopicService(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }

    @Override
    @Transactional
    public Topic create(final Topic topic) {
        Objects.requireNonNull(topic, NULL_TOPIC_ERROR);
        return topicRepository.saveAndFlush(topic);
    }

    @Override
    @Transactional
    public Topic update(final Topic topic) {
        Objects.requireNonNull(topic, NULL_TOPIC_ERROR);
        return topicRepository.saveAndFlush(topic);
    }

    @Override
    @Transactional
    public void delete(final long id) {
        if (id <= 0) {
            log.error(String.format(INVALID_TOPIC_ID_ERROR, id));
            throw new IllegalArgumentException(String.format(INVALID_TOPIC_ID_ERROR, id));
        }
        topicRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void delete(Topic topic) {
        topicRepository.delete(topic);
    }

    public Topic findById(long id) {
        if (id <= 0) {
            log.error(String.format(INVALID_TOPIC_ID_ERROR, id));
            throw new IllegalArgumentException(String.format(INVALID_TOPIC_ID_ERROR, id));
        }
        return topicRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format(TOPIC_NOT_FOUND_BY_ID, id)));
    }

    public List<Topic> getTopics() {
        return topicRepository.findAll();
    }
}
