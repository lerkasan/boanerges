package net.lerkasan.capstone.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name = "roles")
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(of = {"title"})
public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @NotBlank
    @Size(min = 3, max = 100)
    @Column(name = "title", length = 100, unique = true, nullable = false)
    private String title;

    public Role(String title) {
        this.title = title;
    }

    @Override
    public String getAuthority() {
        return getTitle();
    }
}