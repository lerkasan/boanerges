package net.lerkasan.capstone.mapper;


import net.lerkasan.capstone.dto.AnswerDto;
import net.lerkasan.capstone.dto.FeedbackDto;
import net.lerkasan.capstone.model.Answer;
import net.lerkasan.capstone.model.Feedback;
import net.lerkasan.capstone.service.aws.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3Uri;
import software.amazon.awssdk.services.s3.S3Utilities;

import java.net.URI;

@Component
public class FeedbackDtoMapper {

    private final S3Client s3Client;

    private final S3Service s3Service;

//    S3Utilities s3Utilities;

    @Autowired
    public FeedbackDtoMapper(S3Client s3Client, S3Service s3Service) {
        this.s3Client = s3Client;
        this.s3Service = s3Service;
//        this.s3Utilities = this.s3Client.utilities();
    }

    public FeedbackDto toFeedbackDto(Feedback feedback) {
        String audioUrl = feedback.getAudioUrl();
        String bucketName = s3Service.convertS3UrlToBucketAndKey(audioUrl).get("bucket");
        String key = s3Service.convertS3UrlToBucketAndKey(audioUrl).get("key");
        String presignedAudioUrl = s3Service.presignS3Url(bucketName, key);
        return new FeedbackDto(
                feedback.getId(),
                feedback.getText(),
                presignedAudioUrl,
                feedback.getScore());
    }

    public Feedback toFeedback(FeedbackDto feedbackDto) {
        String presignedAudioUrl = feedbackDto.getAudioUrl();
        String audioUrl = s3Service.convertPresignedS3UrlToS3Url(presignedAudioUrl);
        return new Feedback(
                feedbackDto.getId(),
                feedbackDto.getText(),
                audioUrl,
                feedbackDto.getScore()
        );
    }
}
