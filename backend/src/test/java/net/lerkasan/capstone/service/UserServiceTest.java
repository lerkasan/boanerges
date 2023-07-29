package net.lerkasan.capstone.service;

import net.lerkasan.capstone.model.Role;
import net.lerkasan.capstone.model.User;
import net.lerkasan.capstone.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertFalse;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
 class UserServiceTest {

    private static final Role ROLE_USER = new Role(1L, "USER");
    public static final String DUMMY_EMAIL = "dummyEmail";
    public static final String DUMMY_USERNAME = "dummyUsername";
    public static final String DUMMY_EMAIL_COM = "dummy@email.com";
    public static final String DUMMY_PASSWORD = "dummyPassword";

    @Mock
    private UserRepository userRepo;
    @Mock private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userServiceUnderTest;

    @Test
     void shouldReturnFalseIfEmailIsNotAvailable() throws Exception {
        final String email = DUMMY_EMAIL;
        final boolean result = userServiceUnderTest.isEmailAvailable(email);
        assertFalse(result);
    }

    @Test
     void shouldReturnFalseIfEmailIsNull() throws Exception {
        final String email = null;
        final boolean result = userServiceUnderTest.isEmailAvailable(email);
        assertFalse(result);
    }


    @Test
     void shouldReturnFalseIfUsernameIsNotAvailable() throws Exception {
        final String username = DUMMY_USERNAME;
        final boolean result = userServiceUnderTest.isUsernameAvailable(username);
        assertFalse(result);
    }

    @Test
     void shouldReturnFalseIfUsernameIsNull() throws Exception {
        final String username = null;
        final boolean result = userServiceUnderTest.isUsernameAvailable(username);
        assertFalse(result);
    }

    private User createUser() {
        return createUser(DUMMY_USERNAME);
    }

    private User createUser(final String username) {
        User user = new User();
        user.setUsername(username);
        user.setEmail(DUMMY_EMAIL_COM);
        user.setPassword(DUMMY_PASSWORD);
        user.setRawPassword(DUMMY_PASSWORD.toCharArray());
        user.setAuthorities(Collections.singleton(ROLE_USER));
        return user;
    }
}
