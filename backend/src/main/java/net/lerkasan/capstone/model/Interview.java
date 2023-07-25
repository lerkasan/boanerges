package net.lerkasan.capstone.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.CascadeType.DETACH;

@Entity
@Table(name = "interviews")
@Getter
@Setter
@NoArgsConstructor(force = true)
@AllArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(of = {"id", "name"})
@ToString
public class Interview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @NotBlank(message = "Name field is required.")
    @Size(min = 3, max = 500)
    @Column(name = "name", nullable = false, length = 500)
    private String name;

    @JsonIgnore
    //    @JsonFormat(pattern = "dd-MM-yyyy")
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
//    @JsonIgnoreProperties(value = {"interviews"})
    private User user;

    public Interview(Long id, @NonNull String name, Topic topic, Set<Question> questions) {
        this.id = id;
        this.name = name;
        this.topic = topic;
        this.questions = questions;
        this.createdAt = LocalDate.now();
    }
}
