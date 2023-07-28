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

    private Long id;

    @NonNull
    @NotBlank(message = "Text field is required.")
    private String text;

    @NonNull
    @NotBlank(message = "AudioUrl field is required.")
    private String audioUrl;

    private List<AnswerDto> answers;

//    public QuestionDto(Long id, String text, String presignedAudioUrl, List<AnswerDto> answerDtos) {
//        this.id = id;
//        this.text = text;
//        this.audioUrl = presignedAudioUrl;
//        this.answers = answerDtos;
//    }
}