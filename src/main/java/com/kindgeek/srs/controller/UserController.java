package com.kindgeek.srs.controller;

import com.kindgeek.srs.dto.response.UserInfoResponse;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.oidc.StandardClaimNames;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    @GetMapping("/me")
    public UserInfoResponse getUserInfo(JwtAuthenticationToken auth) {
        return UserInfoResponse.builder()
                .username(auth.getToken().getClaimAsString(StandardClaimNames.PREFERRED_USERNAME))
                .email(auth.getToken().getClaimAsString(StandardClaimNames.EMAIL))
                .roles(auth.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList())
                .build();
    }
}
