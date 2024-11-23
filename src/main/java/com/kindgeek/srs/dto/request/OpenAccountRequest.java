package com.kindgeek.srs.dto.request;

import jakarta.validation.constraints.NotNull;

public record OpenAccountRequest(@NotNull String name) {
}
