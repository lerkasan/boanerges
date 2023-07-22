package net.lerkasan.capstone.config;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import net.lerkasan.capstone.config.jwt.JwtTokenFilter;


@Configuration
@EnableScheduling
@EnableWebSecurity
public class SecurityConfig {

    private final PasswordEncoder passwordEncoder;

//    private AuthenticationSuccessHandler authenticationSuccessHandler;

    private final UserDetailsService userDetailsService;

    private final JwtTokenFilter jwtAuthenticationFilter;

//    private final GoogleOAuth2UserService oAuth2UserService;

    @Autowired
    public SecurityConfig(PasswordEncoder passwordEncoder, /* AuthenticationSuccessHandler authenticationSuccessHandler, */ UserDetailsService userDetailsService,
                          JwtTokenFilter jwtAuthenticationFilter) {
        this.passwordEncoder = passwordEncoder;
//        this.authenticationSuccessHandler = authenticationSuccessHandler;
        this.userDetailsService = userDetailsService;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }


//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
//    }



    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userDetailsService);
//        auth.setPasswordEncoder(bCryptPasswordEncoder());
        auth.setPasswordEncoder(passwordEncoder);
        return auth;
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf(AbstractHttpConfigurer::disable)
//                .authorizeHttpRequests((auth) -> auth.anyRequest().permitAll());

        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/", "/api/v1/auth/login", "/css/*", "/js/*", "/api/v1/signup").permitAll()
                                .requestMatchers("/home").hasAnyRole("USER", "ADMIN")
                                .anyRequest().authenticated()

                )
                .sessionManagement(sessionManagement -> sessionManagement
                        .sessionFixation().migrateSession()
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .exceptionHandling( exceptionHandling -> exceptionHandling
                        .authenticationEntryPoint(
                                (request, response, authException)
                                        -> response.sendError(
                                        HttpServletResponse.SC_UNAUTHORIZED,
                                        authException.getLocalizedMessage()
                                )
                        )
                )
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .logout(
                        logout -> logout
                                .invalidateHttpSession(true)
                                .clearAuthentication(true)
                                .logoutUrl("/api/v1/logout")
                                .logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler(HttpStatus.OK))
                                .logoutRequestMatcher(new AntPathRequestMatcher("/api/v1/logout"))
                                .permitAll()
                );
        return http.build();

    }
}