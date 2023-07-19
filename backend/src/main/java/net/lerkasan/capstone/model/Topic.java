package net.lerkasan.capstone.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.validation.annotation.Validated;

@Entity
@Table(name = "topics")
@Getter
@Setter
@NoArgsConstructor(force = true)
@AllArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(of = {"id", "name"})
@ToString
public class Topic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NonNull
    @NotBlank(message = "Name field is required.")
    @Size(min = 3, max = 500)
    @Column(name = "name", nullable = false, length = 500)
    private String name;

    @ManyToOne
    @PrimaryKeyJoinColumn(name="category_id", referencedColumnName="id")
    @JsonIgnoreProperties(value = {"topics"})
    private Category category;

}