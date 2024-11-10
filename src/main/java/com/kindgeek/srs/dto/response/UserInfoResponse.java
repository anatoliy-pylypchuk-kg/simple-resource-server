package com.kindgeek.srs.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record UserInfoResponse(String username, String email, List<String> roles) {
}
