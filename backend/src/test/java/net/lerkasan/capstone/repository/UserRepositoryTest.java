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

    public static final String DUMMY_FIRST_NAME = "dummyFirstName";
    public static final String DUMMY_EMAIL_COM = "dummy@email.com";
    public static final String DUMMY_PASSWORD = "dummyPassword";
    public static final String DUMMY_USERNAME = "dummyUsername";
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    UserRepository userRepositoryUnderTest;

    private User createUser(final String username) {
        User user = new User();
        user.setUsername(username);
        user.setFirstName(DUMMY_FIRST_NAME);
        user.setEmail(DUMMY_EMAIL_COM);
        user.setPassword(DUMMY_PASSWORD);
        user.setRawPassword(DUMMY_PASSWORD.toCharArray());
        return user;
    }

    @Test
    void shouldReturnTrueIfUsernameIsAvailable() {
        final String username = DUMMY_USERNAME;
        assertTrue(userRepositoryUnderTest.isUsernameAvailable(username));
    }

    @Test
    void shouldReturnTrueIfEmailIsAvailable() {
        final String email = DUMMY_EMAIL_COM;
        assertTrue(userRepositoryUnderTest.isEmailAvailable(email));
    }

    @Test
    void shouldReturnFalseIfUsernameIsNotAvailable() {
        final String username = DUMMY_USERNAME;
        User dummyUser = createUser(username);
        entityManager.persist(dummyUser);
        assertFalse(userRepositoryUnderTest.isUsernameAvailable(username));
    }

    @Test
    void shouldReturnFalseIfEmailIsNotAvailable() {
        final String email = DUMMY_EMAIL_COM;
        User dummyUser = createUser(DUMMY_USERNAME);
        entityManager.persist(dummyUser);
        assertFalse(userRepositoryUnderTest.isEmailAvailable(email));
    }



}
