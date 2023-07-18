package net.lerkasan.capstone.service;

import reactor.core.publisher.Mono;

public interface ChatService {
    String sendPrompt(String prompt);
//    Mono<String> sendPrompt(String prompt);
}
