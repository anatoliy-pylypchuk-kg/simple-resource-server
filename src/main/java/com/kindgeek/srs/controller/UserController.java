package com.kindgeek.srs.controller;

import com.kindgeek.srs.dto.response.UserInfoResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.StandardClaimNames;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
