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
public class ChatGptServiceI implements ChatServiceI {

    @Qualifier("ChatGptWebClient")
    private final WebClient chatGptWebClient;

    private final ObjectMapper objectMapper;

    private final ChatRequestBody requestBody;

//    @Value("${openai.chatgpt.model}")
//    private String model;

    @Autowired
    public ChatGptServiceI(WebClient chatGptWebClient, ObjectMapper objectMapper, ChatRequestBody requestBody) {
        this.chatGptWebClient = chatGptWebClient;
        this.objectMapper = objectMapper;
        this.requestBody = requestBody;
    }

//    public Mono<ChatResponseBody> sendPrompt(String prompt) {
//    public ChatResponseBody sendPrompt(String prompt) {
    public String sendPrompt(String prompt) {
//    public String sendPrompt(String prompt, String model) {
//    public Mono<String> sendPrompt(String prompt) {
//    public Flux<String> sendPrompt(String prompt) {

//        https://api.openai.com/v1/chat/completions
        String requestBodyJson ="";
//        ChatRequestBody requestBody = new ChatRequestBody(prompt);
        requestBody.setMessages(List.of(new Message("user", prompt)));
        try {
            requestBodyJson = objectMapper.writeValueAsString(requestBody);
            System.out.println("Request body:");
            System.out.println(requestBodyJson);
        } catch (JsonProcessingException e) {
            log.error("Error converting ChatRequestBody to JSON " + e.getMessage());
        }
        return chatGptWebClient
                .post()
                .bodyValue(requestBodyJson)
//                .accept(MediaType.TEXT_EVENT_STREAM)
                .retrieve()
//                .bodyToFlux(ChatResponseBody.class)
                .bodyToMono(ChatResponseBody.class)
//                .map(responseBody -> responseBody.getChoices().get(0).getDelta().getContent()
                .map(responseBody -> responseBody.getChoices().get(0).getMessage().getContent()
                )
                .block();


//        UriSpec<WebClient.RequestBodySpec> uriSpec = chatGptWebClient.method(HttpMethod.POST);
//        WebClient.RequestHeadersSpec<?> headersSpec = bodySpec.body(
//                Mono.just(requestBody), ChatRequestBody.class);
//        Mono<String> responseBody = headersSpec.exchangeToMono(response -> {
//            if (response.statusCode().equals(HttpStatus.OK)) {
//                return response.bodyToMono(String.class);
//            } else if (response.statusCode().is4xxClientError()) {
//                return Mono.just("Error response");
//            } else {
//                return response.createException()
//                        .flatMap(Mono::error);
//            }
//        });

    }
}
