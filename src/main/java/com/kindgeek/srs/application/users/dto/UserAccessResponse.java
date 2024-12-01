package com.kindgeek.srs.application.users.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record UserAccessResponse(List<UserAccessResource> allowedResources) {
}
