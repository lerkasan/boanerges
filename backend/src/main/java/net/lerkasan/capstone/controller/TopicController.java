package net.lerkasan.capstone.controller;

import net.lerkasan.capstone.model.Book;
import net.lerkasan.capstone.model.Topic;
import net.lerkasan.capstone.service.TopicServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/topics")
public class TopicController {

    private final TopicServiceI topicService;

    @Autowired
    public TopicController(TopicServiceI topicService) {
        this.topicService = topicService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Topic> getTopics() {
        return topicService.getTopics();
    }

    @GetMapping("/{id}")
    public Topic getTopic(@PathVariable Long id) {
        return topicService.findById(id);
    }
}
