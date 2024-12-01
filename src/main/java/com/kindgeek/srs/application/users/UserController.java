package com.kindgeek.srs.application.users;

import com.kindgeek.srs.application.users.dto.UserAccessResource;
import com.kindgeek.srs.application.users.dto.UserAccessResponse;
import com.kindgeek.srs.application.users.dto.UserInfoResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.keycloak.AuthorizationContext;
import org.keycloak.authorization.client.AuthorizationDeniedException;
import org.keycloak.authorization.client.AuthzClient;
import org.keycloak.authorization.client.ClientAuthorizationContext;
import org.keycloak.representations.idm.authorization.AuthorizationRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.StandardClaimNames;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import static java.util.Map.entry;

@RestController
@RequestMapping("/users")
@Tag(name = "users")
public class UserController {

    private static final Map<String, UserAccessResource> RESOURCES = Map.ofEntries(
            entry("Accounts Resource", UserAccessResource.ACCOUNTS),
            entry("Cards Resource", UserAccessResource.CARDS)
    );

    @GetMapping("/me")
    @Operation(operationId = "get-user-info", summary = "Get current user")
    public UserInfoResponse getUserInfo(@AuthenticationPrincipal Jwt token) {
        return UserInfoResponse.builder()
                .username(token.getClaimAsString(StandardClaimNames.PREFERRED_USERNAME))
                .email(token.getClaimAsString(StandardClaimNames.EMAIL))
                .firstName(token.getClaimAsString(StandardClaimNames.GIVEN_NAME))
                .lastName(token.getClaimAsString(StandardClaimNames.FAMILY_NAME))
                .build();
    }

    @GetMapping("/access")
    @Operation(operationId = "get-user-access", summary = "Get current user's access rights")
    public UserAccessResponse getUserAccess(@AuthenticationPrincipal Jwt token, HttpServletRequest request) {
        var context = (ClientAuthorizationContext) request.getAttribute(AuthorizationContext.class.getName());
        var client = context.getClient();

        return UserAccessResponse.builder()
                .allowedResources(RESOURCES.entrySet()
                        .stream()
                        .filter(entry ->
                                hasAccessToResource(client, token.getTokenValue(), entry.getKey()))
                        .map(Map.Entry::getValue)
                        .toList())
                .build();
    }

    private boolean hasAccessToResource(AuthzClient client, String userToken, String resourceName) {
        try {
            var protectionResource = client.protection();

            var resource = protectionResource.resource().findByName(resourceName);

            var request = new AuthorizationRequest();
            request.addPermission(resource.getId());
            request.setPct(userToken);

            var tokenResponse = client.authorization(userToken).authorize(request);
            var result = protectionResource.introspectRequestingPartyToken(tokenResponse.getToken());

            return result.isActive();
        } catch (AuthorizationDeniedException e) {
            return false;
        }
    }
}
