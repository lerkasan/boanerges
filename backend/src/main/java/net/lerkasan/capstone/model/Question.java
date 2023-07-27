package net.lerkasan.capstone.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "questions")
@Getter
@Setter
@NoArgsConstructor(force = true)
@AllArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(of = {"id", "text"})
@ToString(exclude = {"interview", "answer"})
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

    @OneToMany(mappedBy = "question", fetch = FetchType.LAZY) //
    private Set<Answer> answers;

//    @OneToOne(mappedBy = "question") //
//    private Answer answer;

    public Question(Long id, @NonNull String text, @NonNull String audioUrl, Interview interview) {
        this.id = id;
        this.text = text;
        this.audioUrl = audioUrl;
        this.interview = interview;
    }

    public Question(Long id, String text, String audioUrl) {
        this.id = id;
        this.text = text;
        this.audioUrl = audioUrl;
    }
}