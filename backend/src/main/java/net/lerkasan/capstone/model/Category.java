package net.lerkasan.capstone.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.validation.annotation.Validated;

import java.util.HashSet;
import java.util.Set;

import static jakarta.persistence.CascadeType.*;

@Entity
@Table(name = "categories")
@Getter
@Setter
@NoArgsConstructor(force = true)
@AllArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(of = {"id", "name"})
@ToString(exclude = {"topics"})
public class Category {

    public static final String NAME_FIELD_IS_REQUIRED = "Name field is required.";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @NotBlank(message = NAME_FIELD_IS_REQUIRED)
    @Size(min = 3, max = 500)
    @Column(name = "name", nullable = false, length = 500)
    private String name;

    @OneToMany(mappedBy = "category", cascade = {PERSIST, MERGE, REFRESH, DETACH})
    @OrderBy(value = "id ASC")
    private Set<Topic> topics = new HashSet<>();
}
