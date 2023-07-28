package net.lerkasan.capstone.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;
import net.lerkasan.capstone.model.Question;

import java.time.LocalDate;
import java.util.Set;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;
import static com.fasterxml.jackson.annotation.JsonProperty.Access.READ_ONLY;
import static com.fasterxml.jackson.annotation.JsonProperty.Access.WRITE_ONLY;
import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.CascadeType.DETACH;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
@EqualsAndHashCode
//@Validated
@JsonInclude(NON_NULL)
public class InterviewDto {

    private Long id;

    @NonNull
    @NotBlank(message = "Name field is required.")
    @Size(min = 3, max = 500)
    private String name;

    @NonNull
    @Positive(message = "TopicId must be positive.")
    private Long topicId;

//    @JsonProperty(access = READ_ONLY)
//    @JsonFormat(pattern = "dd-MM-yyyy")
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
