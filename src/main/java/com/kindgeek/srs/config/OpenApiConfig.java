package com.kindgeek.srs.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    public static final String JWT_AUTH_KEY = "JSON Web Token";
    public static final String OIDC_KEY = "OpenID Connect";

    @Bean
    public OpenAPI openApi(ResourceServerProperties props) {
        return new OpenAPI()
                .info(new Info()
                        .title(props.swagger().title())
                        .version(props.swagger().version()))
                .servers(List.of(new Server()
                        .description(props.swagger().serverName())
                        .url(props.swagger().serverUri())))
                .components(new Components()
                        .addSecuritySchemes(JWT_AUTH_KEY, new SecurityScheme()
                                .name(JWT_AUTH_KEY)
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT"))
                        .addSecuritySchemes(OIDC_KEY, new SecurityScheme()
                                .name(OIDC_KEY)
                                .type(SecurityScheme.Type.OPENIDCONNECT)
                                .openIdConnectUrl(props.swagger().openIdConfigUri())))
                .addSecurityItem(new SecurityRequirement().addList(JWT_AUTH_KEY))
                .addSecurityItem(new SecurityRequirement().addList(OIDC_KEY));
    }
}
