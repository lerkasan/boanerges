package net.lerkasan.capstone.service;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import net.lerkasan.capstone.dto.QuestionDto;
import net.lerkasan.capstone.exception.NotFoundException;
import net.lerkasan.capstone.model.Question;
import net.lerkasan.capstone.model.Topic;
import net.lerkasan.capstone.repository.QuestionRepository;
import net.lerkasan.capstone.service.aws.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.polly.model.OutputFormat;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static net.lerkasan.capstone.service.InterviewServiceI.INVALID_INTERVIEW_ID_ERROR;
import static net.lerkasan.capstone.service.TopicServiceI.NULL_TOPIC_ERROR;

@Slf4j
@Service
public class QuestionService implements QuestionServiceI {

    private final QuestionRepository questionRepo;

    private final ChatServiceI chatGptService;

    private final SpeechServiceI pollySpeechService;

    private final S3Service s3Service;

    @Autowired
    public QuestionService(QuestionRepository questionRepo, ChatServiceI chatGptService, SpeechServiceI pollySpeechService, S3Service s3Service) {
        this.questionRepo = questionRepo;
        this.chatGptService = chatGptService;
        this.pollySpeechService = pollySpeechService;
        this.s3Service = s3Service;
    }

    @Override
    @Transactional
    public Question create(final Question question) {
        Objects.requireNonNull(question, NULL_QUESTION_ERROR);
        return questionRepo.saveAndFlush(question);
    }

    @Override
    @Transactional
    public Question update(final Question question) {
        Objects.requireNonNull(question, NULL_QUESTION_ERROR);
        return questionRepo.saveAndFlush(question);
    }

    @Override
    @Transactional
    public void delete(final long id) {
        if (id <= 0) {
            log.error(String.format(INVALID_QUESTION_ID_ERROR, id));
            throw new IllegalArgumentException(String.format(INVALID_QUESTION_ID_ERROR, id));
        }
        questionRepo.deleteById(id);
    }

    @Override
    @Transactional
    public void delete(Question question) {
        questionRepo.delete(question);
    }

    @Override
    public QuestionDto generateQuestion(Topic topic) {
        Objects.requireNonNull(topic, NULL_TOPIC_ERROR);
        String s3PresignedUrl = "";
        String textResponse = chatGptService.sendPrompt("You are interviewing a candidate for a Software Developer job. Please ask exactly one question about " + topic.getName() + ". Please prioritize unconventional questions about theoretical fundamentals of the mentioned topic. Make your question longer, around 50 to 60 words. Do not repeat yourself. Do not refer to the prompt.");
        try (InputStream speech = pollySpeechService.synthesizeSpeech(textResponse, "Matthew", OutputFormat.MP3)) {
            UUID uuid = UUID.randomUUID();
            int hashCode = textResponse.hashCode();
            File file = new File("/tmp/polly-" + uuid + hashCode + ".mp3");
            s3Service.copyInputStreamToFile(speech, file);
            s3PresignedUrl = s3Service.uploadToS3(file, "boanerges-radio-voice", "question-audio-" + uuid + hashCode + ".mp3");
        } catch (IOException e) {
            log.error("Error while generating question audio " + e.getMessage());
        }
        return new QuestionDto(textResponse, s3PresignedUrl);
    }

    @Override
    public Question findByIdAndInterviewId(Long questionId, Long interviewId) {
        if (questionId <= 0) {
            log.error(String.format(INVALID_QUESTION_ID_ERROR, questionId));
            throw new IllegalArgumentException(String.format(INVALID_QUESTION_ID_ERROR, questionId));
        }
        if (interviewId <= 0) {
            log.error(String.format(INVALID_INTERVIEW_ID_ERROR, interviewId));
            throw new IllegalArgumentException(String.format(INVALID_INTERVIEW_ID_ERROR, interviewId));
        }
        return questionRepo.findByIdAndInterviewId(questionId, interviewId).orElseThrow(() -> new NotFoundException(String.format(QUESTION_NOT_FOUND, questionId, interviewId)));
    }

    @Override
    public List<Question> getQuestions() {
        return questionRepo.findAll();
    }
}
