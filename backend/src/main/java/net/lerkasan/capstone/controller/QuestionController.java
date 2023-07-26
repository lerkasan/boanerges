package net.lerkasan.capstone.controller;

import jakarta.validation.Valid;
import net.lerkasan.capstone.dto.QuestionDto;
import net.lerkasan.capstone.dto.chatgpt.ChatResponseWithAudio;
import net.lerkasan.capstone.mapper.QuestionDtoMapper;
import net.lerkasan.capstone.model.Interview;
import net.lerkasan.capstone.model.Question;
import net.lerkasan.capstone.model.Topic;
import net.lerkasan.capstone.model.User;
import net.lerkasan.capstone.service.*;
import net.lerkasan.capstone.service.aws.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import software.amazon.awssdk.services.polly.model.OutputFormat;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

@RestController
@RequestMapping
//@RequestMapping("/api/v1/questions")
public class QuestionController {

    @Qualifier("ChatGptService")
    private final ChatServiceI chatGptService;

    private final SpeechServiceI pollySpeechServiceI;
    private final S3Service s3Service;

    private final TopicServiceI topicService;

    private final QuestionDtoMapper questionDtoMapper;

    private final QuestionServiceI questionService;

    private final InterviewServiceI interviewService;

    private final UserServiceI userService;

    @Autowired
    public QuestionController(ChatServiceI chatGptService, SpeechServiceI pollySpeechServiceI, S3Service s3Service, TopicServiceI topicService, QuestionDtoMapper questionDtoMapper, QuestionServiceI questionService, InterviewServiceI interviewService, UserServiceI userService) {
        this.chatGptService = chatGptService;
        this.pollySpeechServiceI = pollySpeechServiceI;
        this.s3Service = s3Service;
        this.topicService = topicService;
        this.questionDtoMapper = questionDtoMapper;
        this.questionService = questionService;
        this.interviewService = interviewService;
        this.userService = userService;
    }

    @GetMapping("/api/v1/questions")
//    @GetMapping(path = "/chat")
    public ChatResponseWithAudio generateQuestion(@RequestParam Long topicId) {
        Topic topic = topicService.findById(topicId);

//    public String getChatResponse() {
//    public Mono<String> getChatResponse() {
//    public Flux<ChatResponseBody> getChatResponse() {
        String s3PresignedUrl = "";
        String textResponse = chatGptService.sendPrompt("You are interviewing a candidate for a Software Developer job. Please ask exactly one question about " + topic.getName() + ". Please prioritize unconventional questions about theoretical fundamentals of the mentioned topic. Make your question longer, around 50 to 60 words. Do not repeat yourself. Do not ask any follow-up questions. Do not refer to the prompt.");
//        String textResponse = chatGptService.sendPrompt("What are the most frequent behavioral questions on tech interviews?");
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
        try (InputStream speech = pollySpeechServiceI.synthesizeSpeech(textResponse, "Matthew", OutputFormat.MP3)) {
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

    @PostMapping("/api/v1/interviews/{interviewId}/questions")
    public ResponseEntity<QuestionDto> saveQuestion(@PathVariable long interviewId, @Valid @RequestBody QuestionDto questionDto, Authentication authentication) {
//        User currentUser = userService.findByUsername(authentication.getName());
        User currentUser = userService.getCurrentUser();
        Interview interview = interviewService.findByIdAndUserId(interviewId, currentUser.getId());
        Question question = questionDtoMapper.toQuestion(questionDto);
        question.setInterview(interview);
        Question createdQuestion = questionService.create(question);
        QuestionDto createdQuestionDto = questionDtoMapper.toQuestionDto(createdQuestion);

        final URI location = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(createdQuestion.getId())
                .toUri();
        return ResponseEntity.created(location).body(createdQuestionDto);
    }
}
