package net.lerkasan.capstone.service;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import net.lerkasan.capstone.constraint.UniqueValidatable;
import net.lerkasan.capstone.exception.NotFoundException;
import net.lerkasan.capstone.model.Role;
import net.lerkasan.capstone.model.User;
import net.lerkasan.capstone.repository.RoleRepository;
import net.lerkasan.capstone.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.CharBuffer;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.*;

import static java.util.Objects.nonNull;
import static net.lerkasan.capstone.repository.RoleRepository.ROLE_USER;

@Slf4j
@Service
public class UserService implements UserServiceI, UserDetailsService, UniqueValidatable {

    private static final int TOKEN_EXPIRE_IN_DAYS = 1;
    public static final String ROLE_WAS_NOT_FOUND = "The role was not found.";
    public static final String TOKEN_NOT_FOUND_IT_MAY_HAVE_EXPIRED = "Token not found. It may have expired.";
    public static final String USERNAME = "username";
    public static final String EMAIL = "email";
    public static final String UNEXPECTED_FIELD_WAS_PASSED_TO_IS_AVAILABLE_METHOD = "Unexpected field was passed to isAvailable method.";
    private final UserRepository userRepo;

    private final RoleRepository roleRepo;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(final UserRepository userRepo, RoleRepository roleRepo, final PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<User> findAll() {
        return userRepo.findAll();
    }

    public User findById(long id) {
        return userRepo.findById(id).orElseThrow(() -> new NotFoundException(String.format(USER_NOT_FOUND_BY_ID, id)));
    }

    public User findByUsername(String username) {
        return userRepo.findByUsername(username).orElseThrow(() -> new NotFoundException(String.format(USER_NOT_FOUND, username)));
    }

    public User findByEmail(String email) {
        return userRepo.findByEmail(email).orElseThrow(() -> new NotFoundException(String.format(USER_NOT_FOUND, email)));
    }

    @Transactional
    @Override
    public User create(User user) {
        final Set<Role> authorities = user.getAuthorities();
        if (authorities == null || authorities.isEmpty()) {
            final Role userRole = roleRepo.findByTitle(ROLE_USER)
                    .orElseThrow(() -> new NotFoundException(ROLE_WAS_NOT_FOUND));
            user.setAuthorities(Collections.singleton(userRole));
        }
        encodePassword(user);
        return userRepo.saveAndFlush(user);
    }

    private void encodePassword(User user) {
        final char[] rawPassword = user.getRawPassword();
        final CharBuffer buffer = CharBuffer.wrap(rawPassword);
        final String encodedPassword = passwordEncoder.encode(buffer);
        user.setPassword(encodedPassword);
        buffer.clear();
        Arrays.fill(rawPassword, 'x');
    }

    @Override
    public User findByToken(String token) {
        return userRepo.findByToken(token).orElseThrow(() -> new NotFoundException(TOKEN_NOT_FOUND_IT_MAY_HAVE_EXPIRED));
    }

    @Override
    public boolean isEmailAvailable(String email) {
        return nonNull(email) && userRepo.isEmailAvailable(email);
    }

    @Override
    public boolean isUsernameAvailable(String username) {
        return nonNull(username) && userRepo.isUsernameAvailable(username);
    }

    @Override
    public User update(User user) {
        Objects.requireNonNull(user);
        return userRepo.saveAndFlush(user);
    }

    @Override
    @Transactional
    public void delete(final long id) {
        userRepo.deleteById(id);
    }

    @Override
    @Transactional
    public void delete(final User user) {
        userRepo.delete(user);
    }

    @Transactional
    @Scheduled(cron = "${cron.delete.expired.token}")
    public void deleteExpiredTokens() {
        LocalDateTime expireTimestamp = LocalDateTime.now().minusDays(TOKEN_EXPIRE_IN_DAYS);
        userRepo.deleteNotVerifiedExpiredUsers(expireTimestamp);
    }

    @Override
    public boolean isAvailable(String fieldName, String fieldValue) {
        return switch (fieldName) {
            case USERNAME -> isUsernameAvailable(fieldValue);
            case EMAIL -> isEmailAvailable(fieldValue);
            default -> { log.error(UNEXPECTED_FIELD_WAS_PASSED_TO_IS_AVAILABLE_METHOD);
                throw new IllegalArgumentException(UNEXPECTED_FIELD_WAS_PASSED_TO_IS_AVAILABLE_METHOD);
            }
        };
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findByUsername(username).orElseThrow(() -> new NotFoundException(String.format(USER_NOT_FOUND, username)));
    }

    @Override
    public User getCurrentUser() {
        Principal userPrincipal = SecurityContextHolder.getContext().getAuthentication();
        return findByUsername(userPrincipal.getName());
    }
}
