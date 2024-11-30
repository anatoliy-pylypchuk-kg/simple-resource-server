package com.kindgeek.srs.config;

import com.kindgeek.srs.properties.ResourceServerProperties;
import jakarta.servlet.Filter;
import lombok.SneakyThrows;
import org.keycloak.adapters.authorization.integration.jakarta.ServletPolicyEnforcerFilter;
import org.keycloak.representations.adapters.config.PolicyEnforcerConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.server.resource.web.authentication.BearerTokenAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.util.Map;
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
    SecurityFilterChain securityFilterChain(HttpSecurity http, ResourceServerProperties props) {
        http.oauth2ResourceServer(server -> server.jwt(withDefaults()));

        http.addFilterAfter(policyEnforcerFilter(props.keycloak()), BearerTokenAuthenticationFilter.class);

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

    private Filter policyEnforcerFilter(ResourceServerProperties.Keycloak props) {
        var config = createPolicyEnforcerConfig(props);
        var policyEnforcerFilter = new ServletPolicyEnforcerFilter(request -> config);

        return new ConditionallyDisabledFilter(policyEnforcerFilter, ALLOW_ALL_REQUEST_MATCHERS);
    }

    private PolicyEnforcerConfig createPolicyEnforcerConfig(ResourceServerProperties.Keycloak props) {
        var config = new PolicyEnforcerConfig();
        config.setAuthServerUrl(props.baseUrl());
        config.setRealm(props.realm());
        config.setResource(props.clientId());
        config.setCredentials(Map.of("secret", props.clientSecret()));
        config.setEnforcementMode(PolicyEnforcerConfig.EnforcementMode.ENFORCING);

        return config;
    }
}
