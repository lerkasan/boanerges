package net.lerkasan.capstone.repository;

import jakarta.transaction.Transactional;
import net.lerkasan.capstone.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@Transactional
//@Import(JpaConfig.class)
public class UserRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    UserRepository userRepositoryUnderTest;

    private User createUser(final String username) {
        User user = new User();
        user.setUsername(username);
        user.setFirstName("dummyFirstName");
        user.setEmail("dummy@email.com");
        user.setPassword("dummyPassword");
        user.setRawPassword("dummyPassword".toCharArray());
        return user;
    }

    @Test
    void shouldReturnTrueIfUsernameIsAvailable() {
        final String username = "dummyUsername";
        assertTrue(userRepositoryUnderTest.isUsernameAvailable(username));
    }

    @Test
    void shouldReturnTrueIfEmailIsAvailable() {
        final String email = "dummy@email.com";
        assertTrue(userRepositoryUnderTest.isEmailAvailable(email));
    }

    @Test
    void shouldReturnFalseIfUsernameIsNotAvailable() {
        final String username = "dummyUsername";
        User dummyUser = createUser(username);
        entityManager.persist(dummyUser);
        assertFalse(userRepositoryUnderTest.isUsernameAvailable(username));
    }

    @Test
    void shouldReturnFalseIfEmailIsNotAvailable() {
        final String email = "dummy@email.com";
        User dummyUser = createUser("dummyUsername");
        entityManager.persist(dummyUser);
        assertFalse(userRepositoryUnderTest.isEmailAvailable(email));
    }



}
