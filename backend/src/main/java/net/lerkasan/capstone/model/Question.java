package net.lerkasan.capstone.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.validation.annotation.Validated;

@Entity
@Table(name = "questions")
@Getter
@Setter
@NoArgsConstructor(force = true)
@AllArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(of = {"id", "text"})
@ToString
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @NotBlank(message = "Text field is required.")
    @Column(name = "text", nullable = false)
    private String text;

    @NonNull
    @NotBlank(message = "Audio_url field is required.")
    @Column(name = "audio_url", nullable = false)
    private String audioUrl;

    @ManyToOne
    @PrimaryKeyJoinColumn(name="interview_id", referencedColumnName="id")
    @JsonIgnoreProperties(value = {"questions"})
    private Interview interview;

    @OneToOne(mappedBy = "question")
    private Answer answer;

}