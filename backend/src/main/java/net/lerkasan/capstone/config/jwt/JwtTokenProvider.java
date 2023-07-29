package net.lerkasan.capstone.config.jwt;

import com.google.gson.stream.MalformedJsonException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.security.Key;
import java.util.Date;


@Component
@Slf4j
public class JwtTokenProvider {

    public static final int TOKEN_EXPIRATION = 3600000;
    public static final String AUTHORIZATION = "Authorization";
    public static final String BEARER = "Bearer ";
    public static final String INVALID_JWT_TOKEN = "Invalid JWT token";
    public static final String EXPIRED_JWT_TOKEN = "Expired JWT token";
    public static final String UNSUPPORTED_JWT_TOKEN = "Unsupported JWT token";
    public static final String JWT_CLAIMS_STRING_IS_EMPTY = "JWT claims string is empty";
    public static final String ERROR_WITH_THE_SIGNATURE_OF_TOKEN = "there is an error with the signature of you token ";
    Key key = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    public String createToken(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + TOKEN_EXPIRATION);

        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();
    }


    public String resolveToken(HttpServletRequest request) {

        String bearerToken = request.getHeader(AUTHORIZATION);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER)) {
            return bearerToken.substring(7);
        }
        return null;
    }

    // Check if the token is valid and not expired
    public boolean validateToken(String token) {

        try {
            Jwts.parser().setSigningKey(key).parseClaimsJws(token);
            return true;
//        } catch (MalformedJsonException ex) {
//            log.error("Malformed json");
        } catch (MalformedJwtException ex) {
            log.error(INVALID_JWT_TOKEN);
        } catch (ExpiredJwtException ex) {
            log.error(EXPIRED_JWT_TOKEN);
        } catch (UnsupportedJwtException ex) {
            log.error(UNSUPPORTED_JWT_TOKEN);
        } catch (IllegalArgumentException ex) {
            log.error(JWT_CLAIMS_STRING_IS_EMPTY);
        } catch (SignatureException e) {
            log.error(ERROR_WITH_THE_SIGNATURE_OF_TOKEN);
        }
        return false;
    }

    // Extract the username from the JWT token
    public String getUsername(String token) {

        return Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}