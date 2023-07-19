package net.lerkasan.capstone.repository;

import net.lerkasan.capstone.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    final String EMAIL_AVAILABILITY_QUERY = "SELECT count(u) = 0 FROM User u WHERE LOWER(u.email) = LOWER(:email)";
    final String USERNAME_AVAILABILITY_QUERY = "SELECT count(u) = 0 FROM User u WHERE LOWER(u.username) = LOWER(:username)";
    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    Optional<User> findByToken(String token);

    @Query(EMAIL_AVAILABILITY_QUERY)
    boolean isEmailAvailable(@Param("email") String email);

    @Query(USERNAME_AVAILABILITY_QUERY)
    boolean isUsernameAvailable(@Param("username") String username);
}
