package com.kindgeek.srs.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "simple-resource-server")
public record ResourceServerProperties(SwaggerProperties swagger) {

    public record SwaggerProperties(
            String title,
            String version,
            String serverName,
            String serverUri,
            String openIdConfigUri
    ) {
    }
}
