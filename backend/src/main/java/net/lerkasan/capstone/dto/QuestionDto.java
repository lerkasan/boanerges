package net.lerkasan.capstone.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
@EqualsAndHashCode
//@Validated
@JsonInclude(NON_NULL)
public class QuestionDto {

    public static final String TEXT_FIELD_IS_REQUIRED = "Text field is required.";
    public static final String AUDIO_URL_FIELD_IS_REQUIRED = "AudioUrl field is required.";
    private Long id;

    @NonNull
    @NotBlank(message = TEXT_FIELD_IS_REQUIRED)
    private String text;

    @NonNull
    @NotBlank(message = AUDIO_URL_FIELD_IS_REQUIRED)
    private String audioUrl;

    private List<AnswerDto> answers;

//    public QuestionDto(Long id, String text, String presignedAudioUrl, List<AnswerDto> answerDtos) {
//        this.id = id;
//        this.text = text;
//        this.audioUrl = presignedAudioUrl;
//        this.answers = answerDtos;
//    }
}