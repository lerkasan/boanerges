package net.lerkasan.capstone.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Table(name = "feedbacks")
@Getter
@Setter
@NoArgsConstructor(force = true)
@AllArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(of = {"id", "text"})
@ToString
public class Feedback {

    public static final String TEXT_FIELD_IS_REQUIRED = "Text field is required.";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @NotBlank(message = TEXT_FIELD_IS_REQUIRED)
    @Column(name = "text", nullable = false)
    private String text;

    @NonNull
    @NotBlank
    @Column(name = "audio_url", nullable = false)
    private String audioUrl;

    @Min(0)
    @Max(100)
    @Column(name = "score")
    private Integer score;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="answer_id", referencedColumnName="id")
    private Answer answer;

    public Feedback(Long id, String text, String audioUrl, int score) {
        this.id = id;
        this.text = text;
        this.audioUrl = audioUrl;
        this.score = score;
    }
}
