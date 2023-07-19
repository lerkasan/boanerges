package net.lerkasan.capstone.controller;

import net.lerkasan.capstone.dto.chatgpt.ChatResponseWithAudio;
import net.lerkasan.capstone.service.ChatService;
import net.lerkasan.capstone.service.aws.S3Service;
import net.lerkasan.capstone.service.SpeechService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import software.amazon.awssdk.services.polly.model.OutputFormat;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/api/v1/chat")
public class ChatGptController {

    @Qualifier("ChatGptService")
    private final ChatService chatGptService;

    private final SpeechService pollySpeechService;
    private final S3Service s3Service;

    @Autowired
    public ChatGptController(ChatService chatGptService, SpeechService pollySpeechService, S3Service s3Service) {
        this.chatGptService = chatGptService;
        this.pollySpeechService = pollySpeechService;
        this.s3Service = s3Service;
    }

    @GetMapping
    public ChatResponseWithAudio getChatResponse() {
//    public String getChatResponse() {
//    public Mono<String> getChatResponse() {
//    public Flux<ChatResponseBody> getChatResponse() {
        String s3PresignedUrl = "";
        String textResponse = chatGptService.sendPrompt("What are the most frequent behavioral questions on tech interviews?");
//        Mono<String> response = chatGptService.sendPrompt("What are the most frequent behavioral questions on tech interviews?");
//        Flux<ChatResponseBody> response = chatGptService.sendPrompt("What are the most frequent behavioral questions on tech interviews?");
        System.out.println("Response:" + textResponse);
//        response.subscribe(text -> {
//            try (InputStream speech = pollySpeechService.synthesizeSpeech(text, "Brian", OutputFormat.MP3)) {
//                File file = new File("/tmp/polly-" + response.hashCode() + ".mp3");
//                s3Service.copyInputStreamToFile(speech, file);
//                s3Service.uploadToS3(file, "boanerges-radio-voice", "chat-" + response.hashCode() + ".mp3");
//                System.out.println("Text: " + text);
////                s3Service.uploadToS3(speech, "boanerges-radio-voice", "chat.mp3");
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        });
        try (InputStream speech = pollySpeechService.synthesizeSpeech(textResponse, "Brian", OutputFormat.MP3)) {
            File file = new File("/tmp/polly-" + textResponse.hashCode() + ".mp3");
            s3Service.copyInputStreamToFile(speech, file);
            s3PresignedUrl = s3Service.uploadToS3(file, "boanerges-radio-voice", "chat-" + textResponse.hashCode() + ".mp3");
            System.out.println("Text: " + textResponse);
//                s3Service.uploadToS3(speech, "boanerges-radio-voice", "chat.mp3");
        } catch (IOException e) {
            e.printStackTrace();
        }
//        return response;
        return new ChatResponseWithAudio(textResponse, s3PresignedUrl);
    }
}
