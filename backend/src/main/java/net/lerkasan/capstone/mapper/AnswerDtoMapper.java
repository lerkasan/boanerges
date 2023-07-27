package net.lerkasan.capstone.mapper;

import net.lerkasan.capstone.dto.AnswerDto;
import net.lerkasan.capstone.model.Answer;
import net.lerkasan.capstone.service.aws.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3Uri;
import software.amazon.awssdk.services.s3.S3Utilities;

import java.net.URI;

@Component
public class AnswerDtoMapper {

    private final S3Client s3Client;

    private final S3Service s3Service;

    S3Utilities s3Utilities;

    @Autowired
    public AnswerDtoMapper(S3Client s3Client, S3Service s3Service) {
        this.s3Client = s3Client;
        this.s3Service = s3Service;
        this.s3Utilities = this.s3Client.utilities();
    }

    public AnswerDto toAnswerDto(Answer answer) {
        String audioUrl = answer.getAudioUrl();
        String bucketName = s3Service.convertS3UrlToBucketAndKey(audioUrl).get("bucket");
        String key = s3Service.convertS3UrlToBucketAndKey(audioUrl).get("key");
        String presignedAudioUrl = s3Service.presignS3Url(bucketName, key);
        return new AnswerDto(
                answer.getId(),
                answer.getText(),
                presignedAudioUrl);
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