package com.kindgeek.srs.config;

import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.util.stream.Stream;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;
import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    private static final RequestMatcher[] ALLOW_ALL_REQUEST_MATCHERS = Stream.concat(
                    Stream.of("/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**")
                            .map(AntPathRequestMatcher::new),
                    Stream.of(toH2Console()))
            .toArray(RequestMatcher[]::new);

    @Bean
    @SuppressWarnings({"Convert2MethodRef", "squid:S1612"}) // Lambda-expressions look nicer here
    @SneakyThrows
    SecurityFilterChain securityFilterChain(HttpSecurity http) {
        http.oauth2ResourceServer(server -> server.jwt(withDefaults()));

        http.authorizeHttpRequests(requests -> {
            requests.requestMatchers(ALLOW_ALL_REQUEST_MATCHERS).permitAll();
            requests.anyRequest().authenticated();
        });

        http.sessionManagement(sessions ->
                sessions.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );

        http.csrf(csrf -> csrf.disable());

        http.headers(headers ->
                headers.frameOptions(frameOptions -> frameOptions.sameOrigin())
        );

        return http.build();
    }
}
