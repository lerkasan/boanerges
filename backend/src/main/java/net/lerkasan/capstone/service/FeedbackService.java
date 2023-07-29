package net.lerkasan.capstone.service;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import net.lerkasan.capstone.dto.FeedbackDto;
import net.lerkasan.capstone.exception.NotFoundException;
import net.lerkasan.capstone.model.Answer;
import net.lerkasan.capstone.model.Feedback;
import net.lerkasan.capstone.model.Question;
import net.lerkasan.capstone.repository.FeedbackRepository;
import net.lerkasan.capstone.service.aws.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.polly.model.OutputFormat;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
public class FeedbackService implements FeedbackServiceI {

    public static final String VOICE_ID = "Matthew";
    public static final String MP_3 = ".mp3";
    public static final String TMP_POLLY = "/tmp/polly-";
    public static final String BOANERGES_RADIO_VOICE_BUCKET = "boanerges-radio-voice";
    public static final String QUESTION_AUDIO = "question-audio-";
    public static final String ERROR_WHILE_GENERATING_FEEDBACK_AUDIO_FILE = "Error while generating feedback audio file: ";
    public static final String ERROR_WHILE_PARSING_SCORE = "Error while parsing score: ";
    public static final String GHATGPT_GENERATE_FEEDBACK_PROMPT_PART_ONE = "You are interviewing a candidate for a Software Developer job. Here is a question that you asked: ";
    public static final String GHATGPT_GENERATE_FEEDBACK_PROMPT_PART_TWO = "And here is the text that was transcribed from candidate's spoken response: ";
    public static final String GHATGPT_GENERATE_FEEDBACK_PROMPT_PART_THREE = " Transcribed text might contain minor mistakes like several of wrong words. " +
            "For example some words from candidate's speech might be accidentally transcribed as other words with similar pronunciations. Please ignore minor mistakes. " +
            "Please provide extensive feedback about candidate's answer. Emphasize the ways how the candidate can improve their answer. " +
            "Refer to the candidate as 'you' as if you were telling this response to the candidate directly. " +
            "Do not refer to this prompt and do not repeat any part of this prompt in your response. Do not repeat yourself.";
    public static final String CHATGPT_GENERATE_SCORE_PART_TWO = "And here is the candidate's response: ";
    public static final String CHATGPT_GENERATE_SCORE_PART_THREE = " Please provide a numerical score for the candidate's answer. The score should be between 0 and 100. " +
            "0 means that the candidate's answer was very poor. 100 means that the candidate's answer was excellent. " +
            "Your answer should be limited only to one number that would represent the score. Do not refer to this prompt and do not repeat any part of this prompt in your response. " +
            "Do not repeat yourself.";
    private final FeedbackRepository feedbackRepo;

    private final ChatServiceI chatGptService;

    private final SpeechServiceI pollySpeechService;

    private final S3Service s3Service;

    @Autowired
    public FeedbackService(FeedbackRepository feedbackRepo, ChatServiceI chatGptService, SpeechServiceI pollySpeechService, S3Service s3Service) {
        this.feedbackRepo = feedbackRepo;
        this.chatGptService = chatGptService;
        this.pollySpeechService = pollySpeechService;
        this.s3Service = s3Service;
    }

    @Override
    public Feedback create(Feedback feedback) {
        Objects.requireNonNull(feedback, NULL_FEEDBACK_ERROR);
        return feedbackRepo.saveAndFlush(feedback);
    }

    @Override
    public Feedback update(Feedback feedback) {
        Objects.requireNonNull(feedback, NULL_FEEDBACK_ERROR);
        return feedbackRepo.saveAndFlush(feedback);
    }


    @Override
    public void findById(final long id) {
        if (id <= 0) {
            log.error(String.format(INVALID_FEEDBACK_ID_ERROR, id));
            throw new IllegalArgumentException(String.format(INVALID_FEEDBACK_ID_ERROR, id));
        }
        feedbackRepo.findById(id).orElseThrow(() -> new NotFoundException(String.format(FEEDBACK_NOT_FOUND, id)));
    }

    @Override
    @Transactional
    public void delete(final long id) {
        if (id <= 0) {
            log.error(String.format(INVALID_FEEDBACK_ID_ERROR, id));
            throw new IllegalArgumentException(String.format(INVALID_FEEDBACK_ID_ERROR, id));
        }
        feedbackRepo.deleteById(id);
    }

    @Override
    @Transactional
    public void delete(Feedback feedback) {
        feedbackRepo.delete(feedback);
    }

    @Override
    public FeedbackDto generateFeedback(Answer answer) {
        String s3PresignedUrl = "";
        int score = 100;
        Question question = answer.getQuestion();
        String textResponse = chatGptService.sendPrompt(GHATGPT_GENERATE_FEEDBACK_PROMPT_PART_ONE
                + question.getText() + GHATGPT_GENERATE_FEEDBACK_PROMPT_PART_TWO + answer.getText() +
                GHATGPT_GENERATE_FEEDBACK_PROMPT_PART_THREE);

        if (textResponse.length() >= 3000) {
            textResponse = textResponse.substring(0, 2990);
        }

        try (InputStream speech = pollySpeechService.synthesizeSpeech(textResponse, VOICE_ID, OutputFormat.MP3)) {
            UUID uuid = UUID.randomUUID();
            int hashCode = textResponse.hashCode();
            File file = new File(TMP_POLLY + uuid + hashCode + MP_3);
            s3Service.copyInputStreamToFile(speech, file);
            s3PresignedUrl = s3Service.uploadToS3(file, BOANERGES_RADIO_VOICE_BUCKET, QUESTION_AUDIO + uuid + hashCode + MP_3);
        } catch (IOException e) {
           log.error(ERROR_WHILE_GENERATING_FEEDBACK_AUDIO_FILE + e.getMessage());
        }

        String scoreResponse = chatGptService.sendPrompt(GHATGPT_GENERATE_FEEDBACK_PROMPT_PART_ONE
                + question.getText() + CHATGPT_GENERATE_SCORE_PART_TWO + answer.getText() + CHATGPT_GENERATE_SCORE_PART_THREE);

        try {
            score = Integer.parseInt(scoreResponse);
        }
        catch (NumberFormatException e) {
            log.error(ERROR_WHILE_PARSING_SCORE + e.getMessage());
        }
        return new FeedbackDto(textResponse, s3PresignedUrl, score);
    }
}
