package net.lerkasan.capstone.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.validation.annotation.Validated;

@Entity
@Table(name = "answers")
@Getter
@Setter
@NoArgsConstructor(force = true)
@AllArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(of = {"id", "text"})
@ToString
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @NotBlank
    @Column(name = "text", nullable = false)
    private String text;

    @NonNull
    @NotBlank
    @Column(name = "audio_url", nullable = false)
    private String audioUrl;

//    @OneToOne
    @ManyToOne(fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn(name="question_id", referencedColumnName="id")
    private Question question;

    @OneToOne(mappedBy = "answer")
    private Feedback feedback;

    public Answer(Long id, @NonNull String text, @NonNull String audioUrl) {
        this.id = id;
        this.text = text;
        this.audioUrl = audioUrl;
    }
}