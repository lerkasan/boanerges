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
@JsonInclude(NON_NULL)
public class FeedbackDto {

    public static final String TEXT_FIELD_IS_REQUIRED = "Text field is required.";
    public static final String AUDIO_URL_FIELD_IS_REQUIRED = "AudioUrl field is required.";
    private Long id;

    @NonNull
    @NotBlank(message = TEXT_FIELD_IS_REQUIRED)
    private String text;

    @NonNull
    @NotBlank(message = AUDIO_URL_FIELD_IS_REQUIRED)
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
