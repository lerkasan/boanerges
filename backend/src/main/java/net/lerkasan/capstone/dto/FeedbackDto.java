package net.lerkasan.capstone.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
@EqualsAndHashCode
//@Validated
@JsonInclude(NON_NULL)
public class FeedbackDto {

    private Long id;

    @NonNull
    @NotBlank(message = "Text field is required.")
    private String text;

    @NonNull
    @NotBlank(message = "AudioUrl field is required.")
    private String audioUrl;

    @Min(0)
    @Max(100)
    private int score;

    public FeedbackDto(Long id, @NonNull String text, @NonNull String audioUrl, Integer score) {
        this.id = id;
        this.text = text;
        this.audioUrl = audioUrl;
        this.score = score;
    }

    public FeedbackDto(@NonNull String textResponse, @NonNull String audioUrl, int score) {
        this.text = textResponse;
        this.audioUrl = audioUrl;
        this.score = score;
    }
}
