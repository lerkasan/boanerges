package net.lerkasan.capstone.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import net.lerkasan.capstone.dto.chatgpt.ChatRequestBody;
import net.lerkasan.capstone.dto.chatgpt.ChatResponseBody;
import net.lerkasan.capstone.dto.chatgpt.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Slf4j
@Service
public class ChatGptService implements ChatServiceI {

    public static final String USER = "user";
    public static final String REQUEST_BODY = "Request body:";
    public static final String ERROR_CONVERTING_CHAT_REQUEST_BODY_TO_JSON = "Error converting ChatRequestBody to JSON ";
    @Qualifier("ChatGptWebClient")
    private final WebClient chatGptWebClient;

    private final ObjectMapper objectMapper;

    private final ChatRequestBody requestBody;

    @Autowired
    public ChatGptService(WebClient chatGptWebClient, ObjectMapper objectMapper, ChatRequestBody requestBody) {
        this.chatGptWebClient = chatGptWebClient;
        this.objectMapper = objectMapper;
        this.requestBody = requestBody;
    }

    public String sendPrompt(String prompt) {
        String requestBodyJson ="";
        requestBody.setMessages(List.of(new Message(USER, prompt)));
        try {
            requestBodyJson = objectMapper.writeValueAsString(requestBody);
            log.info(REQUEST_BODY);
            log.info(requestBodyJson);
        } catch (JsonProcessingException e) {
            log.error(ERROR_CONVERTING_CHAT_REQUEST_BODY_TO_JSON + e.getMessage());
        }
        return chatGptWebClient
                .post()
                .bodyValue(requestBodyJson)
                .retrieve()
                .bodyToMono(ChatResponseBody.class)
                .map(responseBody -> responseBody.getChoices().get(0).getMessage().getContent()
                ).block();
    }
}
