package net.lerkasan.capstone.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import net.lerkasan.capstone.constraint.Password;
import net.lerkasan.capstone.constraint.Unique;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;
import static com.fasterxml.jackson.annotation.JsonProperty.Access.WRITE_ONLY;
import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor(force = true)
@AllArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(of = {"username", "email", "createdAt"})
@ToString(exclude = {"rawPassword", "password", "token", "interviews"})
@Validated
@JsonInclude(NON_NULL)
public class User implements UserDetails {
    public static final String USERNAME_FIELD_IS_REQUIRED = "Username field is required.";
    public static final String USERNAME_MUST_BE_BETWEEN_3_AND_25_CHARACTERS_LONG = "Username must be between 3 and 25 characters long.";
    public static final String USERNAME_CHARACTERS = "Username can include only upper and lower case latin letters and digits and start with a letter.";
    public static final int USERNAME_MAX_LENGTH = 25;
    public static final String USERNAME_ALREADY_EXISTS = "Username already exists.";
    public static final String NAME_FIELD_IS_REQUIRED = "First name field is required.";
    public static final String FIRST_NAME_MUST_BE_BETWEEN_3_AND_50_CHARACTERS_LONG = "First name must be between 3 and 50 characters long.";
    public static final String FIRST_NAME_CHARACTERS = "First name can include only upper and lower case latin letters, dashes and spaces.";
    public static final String E_MAIL_FIELD_IS_REQUIRED = "E-mail field is required.";
    public static final String INCORRECT_FORMAT_OF_E_MAIL = "Incorrect format of e-mail.";
    public static final String EMAIL_ALREADY_EXISTS = "Email already exists.";
    public static final String PASSWORD_MUST_BE_BETWEEN_8_AND_32_CHARACTERS_LONG = "Password must be between 8 and 32 characters long.";
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NonNull
    @NotBlank(message = USERNAME_FIELD_IS_REQUIRED)
    @Size(min = 3, max = USERNAME_MAX_LENGTH, message = USERNAME_MUST_BE_BETWEEN_3_AND_25_CHARACTERS_LONG)
    @Pattern(regexp = "^[A-Za-z][A-Za-z0-9]*$", message = USERNAME_CHARACTERS)
    @Unique(field = "username", message = USERNAME_ALREADY_EXISTS)
    @Column(name = "username", nullable = false, unique = true, length = USERNAME_MAX_LENGTH)
    private String username;

    @NonNull
    @NotBlank(message = NAME_FIELD_IS_REQUIRED)
    @Size(min = 3, max = 50, message = FIRST_NAME_MUST_BE_BETWEEN_3_AND_50_CHARACTERS_LONG)
    @Pattern(regexp = "^[a-zA-Z]+(?:[-'\\s][a-zA-Z]+)*$", message = FIRST_NAME_CHARACTERS)
    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;

    @NotBlank(message = E_MAIL_FIELD_IS_REQUIRED)
    @Email(message = INCORRECT_FORMAT_OF_E_MAIL)
    @Unique(field = "email", message = EMAIL_ALREADY_EXISTS)
    @Column(name = "email", unique = true, nullable = false, length = 50)
    private String email;

    @Transient
    @JsonProperty(access = WRITE_ONLY)
    @Size(min = 8, max = 32, message = PASSWORD_MUST_BE_BETWEEN_8_AND_32_CHARACTERS_LONG)
    @Password(message = "Password can include upper and lower case latin letters, numerals (0-9) and special symbols.")
    private char[] rawPassword;

    @JsonIgnore
    @Column(name = "password_hash", length = 60, nullable = false)
    private String password;

    @Column(name = "created_at", nullable = false, updatable = false)
    @JsonIgnore
    private LocalDateTime createdAt = LocalDateTime.now();

    @JsonIgnore
    @Column(length = 64)
    private String token = UUID.randomUUID().toString();

    @Column(name = "enabled", nullable = false)
    private boolean enabled = false;

    @OneToMany(mappedBy = "user", cascade = {PERSIST, MERGE, REFRESH, DETACH})
    @OrderBy(value = "id ASC")
    private Set<Interview> interviews = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    @JsonProperty("roles")
    private Set<Role> authorities = new HashSet<>();

    public boolean grantAuthorities(Role role) {
        return authorities.add(role);
    }

    public boolean revokeAuthorities(Role role) {
        return authorities.remove(role);
    }

    @Override
    public Set<Role> getAuthorities() {
        return Collections.unmodifiableSet(authorities);
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}