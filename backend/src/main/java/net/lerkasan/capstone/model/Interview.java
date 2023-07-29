package net.lerkasan.capstone.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static jakarta.persistence.CascadeType.*;

@Entity
@Table(name = "interviews")
@Getter
@Setter
@NoArgsConstructor(force = true)
@AllArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(of = {"id", "name"})
@ToString(exclude = {"questions"})
public class Interview {

    public static final String NAME_FIELD_IS_REQUIRED = "Name field is required.";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @NotBlank(message = NAME_FIELD_IS_REQUIRED)
    @Size(min = 3, max = 500)
    @Column(name = "name", nullable = false, length = 500)
    private String name;

    @JsonIgnore
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDate createdAt = LocalDate.now();

    @OneToMany(mappedBy = "interview", cascade = {PERSIST, MERGE, REFRESH, DETACH})
    @OrderBy(value = "id ASC")
    private Set<Question> questions = new HashSet<>();

    @ManyToOne
    @PrimaryKeyJoinColumn(name="topic_id", referencedColumnName="id")
    private Topic topic;

    @JsonIgnore
    @ManyToOne
    @PrimaryKeyJoinColumn(name="user_id", referencedColumnName="id")
    private User user;

    public Interview(Long id, @NonNull String name, Topic topic, Set<Question> questions) {
        this.id = id;
        this.name = name;
        this.topic = topic;
        this.questions = questions;
        this.createdAt = LocalDate.now();
    }
}
