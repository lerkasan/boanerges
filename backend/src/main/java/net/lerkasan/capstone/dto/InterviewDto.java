package net.lerkasan.capstone.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;
import java.util.Set;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
@EqualsAndHashCode
@JsonInclude(NON_NULL)
public class InterviewDto {

    public static final String NAME_FIELD_IS_REQUIRED = "Name field is required.";
    public static final String TOPIC_ID_MUST_BE_POSITIVE = "TopicId must be positive.";
    public static final String NAME_CHARACTERS = "Name can include only upper and lower case latin letters, dashes and spaces.";
    private Long id;

    @NonNull
    @NotBlank(message = NAME_FIELD_IS_REQUIRED)
    @Size(min = 3, max = 500)
    private String name;

    @NonNull
    @Positive(message = TOPIC_ID_MUST_BE_POSITIVE)
    private Long topicId;

    @JsonIgnore
    private LocalDate createdAt;

    private Set<QuestionDto> questions;

    public InterviewDto(Long id, @NonNull String name, @NonNull Long topicId, Set<QuestionDto> questions) {
        this.id = id;
        this.name = name;
        this.topicId = topicId;
        this.questions = questions;
    }
}
