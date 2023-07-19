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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @NotBlank(message = "Text field is required.")
    @Column(name = "text", nullable = false)
    private String text;

    @Min(0)
    @Max(100)
    @Column(name = "score")
    private Integer score;

    @OneToOne(cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn(name="answer_id", referencedColumnName="id")
    private Answer answer;
}
