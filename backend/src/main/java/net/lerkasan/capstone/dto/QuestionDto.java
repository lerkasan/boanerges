package net.lerkasan.capstone.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;
import net.lerkasan.capstone.model.Interview;

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
}