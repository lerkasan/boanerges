package net.lerkasan.capstone.mapper;

import net.lerkasan.capstone.dto.QuestionDto;
import net.lerkasan.capstone.model.Question;
import net.lerkasan.capstone.service.aws.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3Uri;
import software.amazon.awssdk.services.s3.S3Utilities;

import java.net.URI;

@Component
public class QuestionDtoMapper {

    private final S3Client s3Client;

    private final S3Service s3Service;

    S3Utilities s3Utilities;

    @Autowired
    public QuestionDtoMapper(S3Client s3Client, S3Service s3Service) {
        this.s3Client = s3Client;
        this.s3Service = s3Service;
        this.s3Utilities = this.s3Client.utilities();
    }

//    private final InterviewServiceI interviewService;
//    private final UserServiceI userService;

//    @Autowired
//    public QuestionDtoMapper(InterviewServiceI interviewService, UserServiceI userService) {
//        this.interviewService = interviewService;
//        this.userService = userService;
//    }

    public QuestionDto toQuestionDto(Question question) {
        String audioUrl = question.getAudioUrl();
        String bucketName = s3Service.convertS3UrlToBucketAndKey(audioUrl).get("bucket");
        String key = s3Service.convertS3UrlToBucketAndKey(audioUrl).get("key");
        String presignedAudioUrl = s3Service.presignS3Url(bucketName, key);
        return new QuestionDto(
                question.getId(),
                question.getText(),
                presignedAudioUrl);
    }

    public Question toQuestion(QuestionDto questionDto) {
        String presignedAudioUrl = questionDto.getAudioUrl();
        String audioUrl = s3Service.convertPresignedS3UrlToS3Url(presignedAudioUrl);
        return new Question(
                questionDto.getId(),
                questionDto.getText(),
                audioUrl
//                interviewService.findByIdAndUserId(questionDto.getInterviewId(), user.getId())
        );
    }
}