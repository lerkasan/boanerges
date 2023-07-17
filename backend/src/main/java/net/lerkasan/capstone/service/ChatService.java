package net.lerkasan.capstone.service;

import reactor.core.publisher.Mono;

public interface ChatService {
    Mono<String> sendPrompt(String prompt);
}
