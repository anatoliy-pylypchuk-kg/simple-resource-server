package com.kindgeek.srs.dto.request;

import jakarta.validation.constraints.NotNull;

public record UpdateAccountRequest(@NotNull String name) {
}
