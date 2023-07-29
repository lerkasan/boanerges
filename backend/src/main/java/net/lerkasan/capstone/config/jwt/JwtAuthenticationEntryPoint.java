package net.lerkasan.capstone.config.jwt;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JwtAuthenticationEntryPoint extends BasicAuthenticationEntryPoint {

    public static final String CONTENT_TYPE = "application/json";
    public static final String JWT_AUTHENTICATION = "JWT Authentication";
    public static final String MESSAGE = "{ \"message\": \"";


    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(CONTENT_TYPE);
        response.getWriter().write(MESSAGE + authException.getMessage() + "\" }");
    }

    @Override
    public void afterPropertiesSet() {
        setRealmName(JWT_AUTHENTICATION);
        super.afterPropertiesSet();
    }
}