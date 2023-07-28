package net.lerkasan.capstone.service;

import net.lerkasan.capstone.dto.FeedbackDto;
import net.lerkasan.capstone.dto.QuestionDto;
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

@Service
public class FeedbackService implements FeedbackServiceI {

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
    public FeedbackDto generateFeedback(Answer answer) {
        String s3PresignedUrl = "";
        int score = 100;
        Question question = answer.getQuestion();
        String textResponse = chatGptService.sendPrompt("You are interviewing a candidate for a Software Developer job. Here is a question that you asked: "
                + question.getText() + "And here is the text that was transcribed from candidate's spoken response: " + answer.getText() +
                " Transcribed text might contain minor mistakes like several of wrong words. " +
                "For example some words from candidate's speech might be accidentally transcribed as other words with similar pronunciations. " +
                "Please ignore minor mistakes. Please provide extensive feedback about candidate's answer. Emphasize the ways how the candidate can improve their answer. " +
                "Refer to the candidate as 'you' as if you were telling this response to the candidate directly. " +
                "Do not refer to this prompt and do not repeat any part of this prompt in your response. Do not repeat yourself.");

        if (textResponse.length() >= 3000) {
            textResponse = textResponse.substring(0, 2990);
        }

        try (InputStream speech = pollySpeechService.synthesizeSpeech(textResponse, "Matthew", OutputFormat.MP3)) {
            UUID uuid = UUID.randomUUID();
            int hashCode = textResponse.hashCode();
            File file = new File("/tmp/polly-" + uuid + hashCode + ".mp3");
            s3Service.copyInputStreamToFile(speech, file);
            s3PresignedUrl = s3Service.uploadToS3(file, "boanerges-radio-voice", "question-audio-" + uuid + hashCode + ".mp3");
        } catch (IOException e) {
            e.printStackTrace();
        }

        String scoreResponse = chatGptService.sendPrompt("You are interviewing a candidate for a Software Developer job. Here is a question that you asked: "
                + question.getText() + "And here is the candidate's response: " + answer.getText() + " Please provide a numerical score for the candidate's answer. " +
                "The score should be between 0 and 100. 0 means that the candidate's answer was very poor. 100 means that the candidate's answer was excellent. " +
                "Your answer should be limited only to one number that would represent the score. Do not refer to this prompt and do not repeat any part of this prompt in your response. Do not repeat yourself.");

        try {
            score = Integer.parseInt(scoreResponse);
        }
        catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return new FeedbackDto(textResponse, s3PresignedUrl, score);
    }
}
