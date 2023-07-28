package net.lerkasan.capstone.service;

import net.lerkasan.capstone.model.Topic;

import java.util.List;

public interface TopicServiceI {
    Topic findById(long id);

    List<Topic> getTopics();

}
