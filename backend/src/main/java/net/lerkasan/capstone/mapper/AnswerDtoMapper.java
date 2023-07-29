package net.lerkasan.capstone.mapper;

import net.lerkasan.capstone.dto.AnswerDto;
import net.lerkasan.capstone.dto.FeedbackDto;
import net.lerkasan.capstone.model.Answer;
import net.lerkasan.capstone.model.Feedback;
import net.lerkasan.capstone.service.aws.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3Utilities;

@Component
public class AnswerDtoMapper {

    public static final String BUCKET = "bucket";
    public static final String KEY = "key";
    private final FeedbackDtoMapper feedbackDtoMapper;

    private final S3Client s3Client;

    private final S3Service s3Service;

    S3Utilities s3Utilities;

    @Autowired
    public AnswerDtoMapper(FeedbackDtoMapper feedbackDtoMapper, S3Client s3Client, S3Service s3Service) {
        this.feedbackDtoMapper = feedbackDtoMapper;
        this.s3Client = s3Client;
        this.s3Service = s3Service;
        this.s3Utilities = this.s3Client.utilities();
    }

    public AnswerDto toAnswerDto(Answer answer) {
        String audioUrl = answer.getAudioUrl();
        String bucketName = s3Service.convertS3UrlToBucketAndKey(audioUrl).get(BUCKET);
        String key = s3Service.convertS3UrlToBucketAndKey(audioUrl).get(KEY);
        String presignedAudioUrl = s3Service.presignS3Url(bucketName, key);
        FeedbackDto feedbackDto = feedbackDtoMapper.toFeedbackDto(answer.getFeedback());
        return new AnswerDto(
                answer.getId(),
                answer.getText(),
                presignedAudioUrl,
                feedbackDto);
    }

    public Answer toAnswer(AnswerDto answerDto) {
        String presignedAudioUrl = answerDto.getAudioUrl();
        String audioUrl = s3Service.convertPresignedS3UrlToS3Url(presignedAudioUrl);
        return new Answer(
                answerDto.getId(),
                answerDto.getText(),
                audioUrl
//                interviewService.findByIdAndUserId(answerDto.getInterviewId(), user.getId())
        );
    }
}