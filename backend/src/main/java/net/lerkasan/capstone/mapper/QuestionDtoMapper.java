package net.lerkasan.capstone.mapper;

import net.lerkasan.capstone.dto.AnswerDto;
import net.lerkasan.capstone.dto.QuestionDto;
import net.lerkasan.capstone.model.Answer;
import net.lerkasan.capstone.model.Question;
import net.lerkasan.capstone.service.AnswerServiceI;
import net.lerkasan.capstone.service.aws.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3Utilities;

import java.util.List;

@Component
public class QuestionDtoMapper {

    public static final String BUCKET = "bucket";
    public static final String KEY = "key";
    private final AnswerServiceI answerService;
    private final AnswerDtoMapper answerDtoMapper;
    private final S3Client s3Client;

    private final S3Service s3Service;

    S3Utilities s3Utilities;

    @Autowired
    public QuestionDtoMapper(AnswerServiceI answerService, AnswerDtoMapper answerDtoMapper, S3Client s3Client, S3Service s3Service) {
        this.answerService = answerService;
        this.answerDtoMapper = answerDtoMapper;
        this.s3Client = s3Client;
        this.s3Service = s3Service;
        this.s3Utilities = this.s3Client.utilities();
    }

    public QuestionDto toQuestionDto(Question question) {
        String audioUrl = question.getAudioUrl();
        String bucketName = s3Service.convertS3UrlToBucketAndKey(audioUrl).get(BUCKET);
        String key = s3Service.convertS3UrlToBucketAndKey(audioUrl).get(KEY);
        String presignedAudioUrl = s3Service.presignS3Url(bucketName, key);
        List<Answer> answers = answerService.findByQuestionId(question.getId());
        List<AnswerDto> answerDtos = toAnswerDtoList(answers);
        return new QuestionDto(
                question.getId(),
                question.getText(),
                presignedAudioUrl,
                answerDtos);
    }

    public Question toQuestion(QuestionDto questionDto) {
        String presignedAudioUrl = questionDto.getAudioUrl();
        String audioUrl = s3Service.convertPresignedS3UrlToS3Url(presignedAudioUrl);
        return new Question(
                questionDto.getId(),
                questionDto.getText(),
                audioUrl
        );
    }

    public List<AnswerDto> toAnswerDtoList(List<Answer> answers) {
        return answers.stream().map(answerDtoMapper::toAnswerDto).toList();
    }
}