package net.lerkasan.capstone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Collections;
import java.util.List;

@SpringBootApplication
public class CapstoneApplication {

    public static final String FRONTEND = "frontend";
    public static final String HTTP_FRONTEND = "http://frontend";
    public static final String LOCALHOST = "localhost";
    public static final String HTTP_LOCALHOST = "http://localhost";
    public static final String LOCALHOST_8080 = "localhost:8080";
    public static final String LOCALHOST_8081 = "localhost:8081";
    public static final String HTTP_LOCALHOST_8080 = "http://localhost:8080";
    public static final String HTTP_LOCALHOST_8081 = "http://localhost:8081";
    public static final String HTTP_LERKASAN_NET = "http://lerkasan.net";
    public static final String HTTPS_LERKASAN_NET = "https://lerkasan.net";

    public static void main(String[] args) {
        SpringApplication.run(CapstoneApplication.class, args);
    }

    @Bean
    public FilterRegistrationBean simpleCorsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.setAllowedOrigins(List.of(FRONTEND, HTTP_FRONTEND, LOCALHOST, HTTP_LOCALHOST, LOCALHOST_8080, HTTP_LOCALHOST_8080, LOCALHOST_8081, HTTP_LOCALHOST_8081, HTTP_LERKASAN_NET, HTTPS_LERKASAN_NET));
        config.setAllowedMethods(Collections.singletonList("*"));
        config.setAllowedHeaders(Collections.singletonList("*"));
        source.registerCorsConfiguration("/**", config);
        FilterRegistrationBean bean = new FilterRegistrationBean<>(new CorsFilter(source));
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return bean;
    }

}
