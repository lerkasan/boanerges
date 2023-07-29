package net.lerkasan.capstone.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import net.lerkasan.capstone.exception.NotFoundException;
import net.lerkasan.capstone.model.User;
import net.lerkasan.capstone.service.*;
import net.lerkasan.capstone.utils.EmailDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Slf4j
@RestController
@RequestMapping("/api/v1")
public class UserController {

    public static final String USERS_ENDPOINT = "/users";
    public static final String ID = "/{id}";
    public static final String SIGNUP_ENDPOINT = "/signup";
    public static final String TOKEN_REQUEST_PARAMETER = "?token=";
    public static final String REGISTRATION_CONFIRMATION = "Boanerges - Registration confirmation";
    public static final String EMAIL_VERIFICATION_ERROR = "Registration link expired or invalid. Please register again.";
    public static final String YOU_CAN_NOW_LOGIN = "Thank you for registering and confirming your email. You can now login. You will be redirected to the home page shortly.";
    public static final String TOKEN = "token";
    public static final String USER_NOT_FOUND_WITH_TOKEN = "User not found with token: ";
    public static final String XXXXXXXXXX = "xxxxxxxxxx";
    public static final String ME = "/me";
    public static final String EMAIL_PARAM = "email";
    public static final String USERNAME_PARAM = "username";
    public static final String SIGNUP_AVAILABLE = "/signup/available";
    private final UserServiceI userService;

    private final HtmlRenderer htmlRender;

    private final EmailServiceI emailService;

    @Autowired
    public UserController(final UserService userService, HtmlRenderer htmlRender, EmailService emailService) {
        this.userService = userService;
        this.htmlRender = htmlRender;
        this.emailService = emailService;
    }

    @PostMapping(USERS_ENDPOINT)
    public ResponseEntity<User> createUser(@Valid @RequestBody final User user) {
        final User registeredUser = userService.create(user);
        final URI location = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path(ID)
                .buildAndExpand(user.getId())
                .toUri();
        return ResponseEntity.created(location).body(registeredUser);
    }

    @PostMapping(SIGNUP_ENDPOINT)
    @ResponseStatus(HttpStatus.OK)
    public void registerUser(@Valid @RequestBody final User user, HttpServletRequest request) {
        User registeredUser = userService.create(user);
        String userEmail = registeredUser.getEmail();
        String requestUrl = request.getRequestURL().toString();
        String verificationUrl = requestUrl + TOKEN_REQUEST_PARAMETER + user.getToken();
        String message = htmlRender.renderVerificationEmail(registeredUser, verificationUrl);
        emailService.sendSimpleMail(new EmailDetails(userEmail, message, REGISTRATION_CONFIRMATION));
    }

    @GetMapping(SIGNUP_ENDPOINT)
    @ResponseStatus(HttpStatus.OK)
    public String confirmRegistration(@RequestParam(TOKEN) String token) {
        String emailVerificationError = EMAIL_VERIFICATION_ERROR;
        String emailVerificationSuccess = YOU_CAN_NOW_LOGIN;
        String body = htmlRender.renderEmailRegistation(emailVerificationError);
        User user;
        try {
            user = userService.findByToken(token);
        } catch (NotFoundException e) {
            log.error(USER_NOT_FOUND_WITH_TOKEN + token);
            return body;
        }
        user.setEnabled(true);
        user.setToken("");
        user.setRawPassword(XXXXXXXXXX.toCharArray());
        userService.update(user);
        body = htmlRender.renderEmailRegistation(emailVerificationSuccess);
        return body;
    }

    @GetMapping(path = SIGNUP_AVAILABLE, params = EMAIL_PARAM)
    public boolean isEmailAvailable(@RequestParam String email) {
        return userService.isEmailAvailable(email);
    }

    @GetMapping(path = SIGNUP_AVAILABLE, params = USERNAME_PARAM)
    public boolean isUsernameAvailable(@RequestParam String username) {
        return userService.isUsernameAvailable(username);
    }

    @GetMapping(path = ME)
    public User getCurrentUserInfo() {
        return userService.getCurrentUser();
    }
}
