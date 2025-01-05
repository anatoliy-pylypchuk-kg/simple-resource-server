package com.kindgeek.srs.application.users;

import com.kindgeek.srs.application.users.dto.UserAccessResponse;
import com.kindgeek.srs.application.users.dto.UserInfoResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.StandardClaimNames;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.kindgeek.srs.application.users.dto.UserAccessResource.ACCOUNTS;
import static com.kindgeek.srs.application.users.dto.UserAccessResource.CARDS;

@RestController
@RequestMapping("/users")
@Tag(name = "users")
public class UserController {

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
    public UserAccessResponse getUserAccess() {
        return UserAccessResponse.builder()
                .allowedResources(List.of(ACCOUNTS, CARDS))
                .build();
    }
}
