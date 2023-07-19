package net.lerkasan.capstone.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import net.lerkasan.capstone.constraint.Password;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.UUID;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;
import static com.fasterxml.jackson.annotation.JsonProperty.Access.WRITE_ONLY;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor(force = true)
@AllArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(of = {"username", "email", "createdAt"})
@ToString
@Validated
@JsonInclude(NON_NULL)
public class User {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NonNull
    @NotBlank(message = "Username field is required.")
    @Size(min = 3, max = 25)
    @Pattern(regexp = "[a-zA-Z]{3,25}$", message = "Username can include only upper and lower case latin letters.")
    @Column(name = "username", nullable = false, unique = true, length = 25)
    private String username;

    @NonNull
    @NotBlank(message = "First name field is required.")
    @Size(min = 3, max = 25)
    @Pattern(regexp = "[a-zA-Z]{3,25}$", message = "First name can include only upper and lower case latin letters.")
    @Column(name = "first_name", nullable = false, length = 25)
    private String firstName;

    @NotBlank(message = "E-mail field is required.")
    @Email(message = "Incorrect format of e-mail.")
    @Column(name = "email", unique = true, nullable = false, length = 50)
    private String email;

    @Transient
    @JsonProperty(access = WRITE_ONLY)
//    @NotNull(message = "Password field is required.")
    @Size(min = 8, max = 32, message = "Password length must be between 8 and 32 characters.")
    @Password(message = "Password can include upper and lower case latin letters, numerals (0-9) and special symbols.")
    private char[] rawPassword;

    @JsonIgnore
    @Column(name = "password_hash", length = 60, nullable = false)
    private String password;

    @Column(name = "created_at", nullable = false, updatable = false)
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
    private LocalDateTime createdAt = LocalDateTime.now();

    @JsonIgnore
    @Column(length = 64)
    private String token = UUID.randomUUID().toString();

    @Column(name = "enabled", nullable = false)
    private boolean enabled = false;
}