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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.notNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
 class UserServiceTest {

    private static final Role ROLE_USER = new Role(1L, "USER");

    @Mock
    private UserRepository userRepo;
    @Mock private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userServiceUnderTest;



    @Test
     void shouldReturnFalseIfEmailIsNotAvailable() throws Exception {
        final String email = "dummyEmail";
//        when(userRepo.isEmailAvailable(email)).thenReturn(false);

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
        final String username = "dummyUsername";
//        when(userRepo.isUsernameAvailable(username)).thenReturn(false);

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
        return createUser("dummyUsername");
    }

    private User createUser(final String username) {
        User user = new User();
        user.setUsername(username);
        user.setEmail("dummy@email.com");
        user.setPassword("dummyPassword");
        user.setRawPassword("dummyPassword".toCharArray());
        user.setAuthorities(Collections.singleton(ROLE_USER));
        return user;
    }
}
