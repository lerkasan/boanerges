package net.lerkasan.capstone.controller;

import net.lerkasan.capstone.dto.chatgpt.ChatResponseWithAudio;
import net.lerkasan.capstone.service.ChatServiceI;
import net.lerkasan.capstone.service.aws.S3Service;
import net.lerkasan.capstone.service.SpeechServiceI;
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
public class ChatController {

    @Qualifier("ChatGptService")
    private final ChatServiceI chatGptService;

    private final SpeechServiceI pollySpeechServiceI;
    private final S3Service s3Service;

    @Autowired
    public ChatController(ChatServiceI chatGptService, SpeechServiceI pollySpeechServiceI, S3Service s3Service) {
        this.chatGptService = chatGptService;
        this.pollySpeechServiceI = pollySpeechServiceI;
        this.s3Service = s3Service;
    }

    @GetMapping
//    @GetMapping(path = "/chat")
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
        try (InputStream speech = pollySpeechServiceI.synthesizeSpeech(textResponse, "Brian", OutputFormat.MP3)) {
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
