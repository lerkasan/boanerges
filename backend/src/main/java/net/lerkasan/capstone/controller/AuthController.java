package net.lerkasan.capstone.controller;

import net.lerkasan.capstone.config.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import net.lerkasan.capstone.dto.auth.AuthenticationResponse;
import net.lerkasan.capstone.dto.auth.AuthenticationRequest;

@RestController
//@CrossOrigin
@RequestMapping(AuthController.AUTH_ENDPOINT)
public class AuthController {

    public static final String AUTH_ENDPOINT = "/api/v1/auth";
    public static final String LOGIN = "/login";
    private final AuthenticationManager authenticationManager;

    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping(LOGIN)
    public AuthenticationResponse authenticateUser(@RequestBody AuthenticationRequest authenticationRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtTokenProvider.createToken(authentication);
        return new AuthenticationResponse(jwt);
    }
}