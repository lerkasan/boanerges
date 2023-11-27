package net.lerkasan.capstone.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/")
public class HomeRedirectController {

    @Value("${frontend.url}")
    private String FRONTEND_URL;
//    public static final String HTTP_LOCALHOST_8080 = "http://localhost:8080";

    @GetMapping("/")
    @ResponseStatus(HttpStatus.PERMANENT_REDIRECT)
    public ResponseEntity<Void> getFrontendHomePage() {

        return ResponseEntity
                .status(HttpStatus.PERMANENT_REDIRECT)
                .location(URI.create(FRONTEND_URL))
//                .location(URI.create(HTTP_LOCALHOST_8080))
                .build();
    }
}
