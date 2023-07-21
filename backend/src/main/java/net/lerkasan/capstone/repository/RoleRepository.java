package net.lerkasan.capstone.repository;

import net.lerkasan.capstone.model.Answer;
import net.lerkasan.capstone.model.Feedback;
import net.lerkasan.capstone.model.Role;
import net.lerkasan.capstone.model.Topic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    String ROLE_USER = "USER";

    Optional<Role> findByTitle(final String title);
}
