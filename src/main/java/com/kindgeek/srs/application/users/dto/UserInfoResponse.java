package com.kindgeek.srs.application.users.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record UserInfoResponse(
        @NotNull String username,
        @NotNull String email,
        @NotNull String firstName,
        @NotNull String lastName
) {
}
