package com.kindgeek.srs.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    @SuppressWarnings({"Convert2MethodRef", "squid:S1612"}) // Lambda-expressions look nicer here
    SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            Converter<Jwt, AbstractAuthenticationToken> authenticationConverter
    ) throws Exception {
        http.oauth2ResourceServer(server -> server
                .jwt(jwtDecoder -> jwtDecoder.jwtAuthenticationConverter(authenticationConverter))
        );

        http.authorizeHttpRequests(requests -> {
            requests.requestMatchers(toH2Console()).permitAll();
            requests.anyRequest().authenticated();
        });

        http.sessionManagement(sessions -> sessions.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.csrf(csrf -> csrf.disable());

        http.headers(headers -> headers.frameOptions(frameOptions -> frameOptions.sameOrigin()));

        return http.build();
    }

    @Bean
    @SuppressWarnings("unchecked")
    AuthoritiesConverter realmRolesAuthoritiesConverter() {
        return claims -> Optional.ofNullable((Map<String, Object>) claims.get("realm_access"))
                .flatMap(map -> Optional.ofNullable((List<String>) map.get("roles")))
                .stream()
                .flatMap(Collection::stream)
                .map(SimpleGrantedAuthority::new)
                .map(GrantedAuthority.class::cast)
                .toList();
    }

    @Bean
    JwtAuthenticationConverter authenticationConverter(
            Converter<Map<String, Object>, Collection<GrantedAuthority>> authoritiesConverter
    ) {
        var authenticationConverter = new JwtAuthenticationConverter();

        authenticationConverter.setJwtGrantedAuthoritiesConverter(
                jwt -> authoritiesConverter.convert(jwt.getClaims())
        );

        return authenticationConverter;
    }
}
