package net.lerkasan.capstone.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import net.lerkasan.capstone.exception.NotFoundException;
import net.lerkasan.capstone.model.User;
import net.lerkasan.capstone.service.EmailService;
import net.lerkasan.capstone.service.UserService;
import net.lerkasan.capstone.service.HtmlRenderer;
import net.lerkasan.capstone.utils.EmailDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/v1")
public class UserController {

    private final UserService userService;

    private final HtmlRenderer htmlRender;

    private final EmailService emailService;

    @Autowired
    public UserController(final UserService userService, HtmlRenderer htmlRender, EmailService emailService) {
        this.userService = userService;
        this.htmlRender = htmlRender;
        this.emailService = emailService;
    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody final User user) {
        final User registeredUser = userService.create(user);
        final URI location = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(user.getId())
                .toUri();
        return ResponseEntity.created(location).body(registeredUser);
    }

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.OK)
    public void registerUser(@Valid @RequestBody final User user, HttpServletRequest request) {
        User registeredUser = userService.create(user);
        String userEmail = registeredUser.getEmail();
        String requestUrl = request.getRequestURL().toString();
        String verificationUrl = requestUrl + "?token=" + user.getToken();
        String message = htmlRender.renderVerificationEmail(registeredUser, verificationUrl);
        emailService.sendSimpleMail(new EmailDetails(userEmail, message, "Boanerges - Registration confirmation"));
    }

    @GetMapping("/signup")
    @ResponseStatus(HttpStatus.OK)
    public String confirmRegistration(@RequestParam("token") String token) {
        String emailVerificationError = "Registration link expired on invalid. Please register again.";
        String emailVerificationSuccess = "Thank you for registering and confirming your email. You can now login.";
        String body = htmlRender.renderEmailRegistation(emailVerificationError);
        User user;
        try {
            user = userService.findByToken(token);
        } catch (NotFoundException e) {
//            return ResponseEntity.ok().body(body);
            return body;
        }
        user.setEnabled(true);
        user.setToken("");
        user.setRawPassword("xxxxxxxxxx".toCharArray());
        userService.update(user);
        body = htmlRender.renderEmailRegistation(emailVerificationSuccess);
        return body;
//        return ResponseEntity.ok().body(body);
    }

//    @GetMapping("available")
//    public boolean isEmailAvailable(@RequestParam String email) {
//        return userService.isEmailAvailable(email);
//    }

}
