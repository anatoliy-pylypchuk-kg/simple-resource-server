package com.kindgeek.srs.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "resource-server")
public record ResourceServerProperties(Keycloak keycloak, SwaggerProperties swagger) {

    public record Keycloak(
            String baseUrl,
            String realm,
            String clientId,
            String clientSecret
    ) {
    }

    public record SwaggerProperties(
            String title,
            String version,
            String serverName,
            String serverUri,
            String openIdConfigUri
    ) {
    }
}
