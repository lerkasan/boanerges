package net.lerkasan.capstone.mapper;


import net.lerkasan.capstone.dto.FeedbackDto;
import net.lerkasan.capstone.model.Feedback;
import net.lerkasan.capstone.service.aws.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.s3.S3Client;

import java.util.Objects;

@Component
public class FeedbackDtoMapper {

    public static final String BUCKET = "bucket";
    public static final String KEY = "key";
    private final S3Client s3Client;

    private final S3Service s3Service;

    @Autowired
    public FeedbackDtoMapper(S3Client s3Client, S3Service s3Service) {
        this.s3Client = s3Client;
        this.s3Service = s3Service;
    }

    public FeedbackDto toFeedbackDto(Feedback feedback) {
        if (Objects.isNull(feedback)) {
            return new FeedbackDto();
        }
        String audioUrl = feedback.getAudioUrl();
        String bucketName = s3Service.convertS3UrlToBucketAndKey(audioUrl).get(BUCKET);
        String key = s3Service.convertS3UrlToBucketAndKey(audioUrl).get(KEY);
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
