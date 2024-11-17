package com.kindgeek.srs.dto.response;

import lombok.Builder;

@Builder
public record UserInfoResponse(String username, String email, String firstName, String lastName) {
}
