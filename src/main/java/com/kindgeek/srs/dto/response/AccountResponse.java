package com.kindgeek.srs.dto.response;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record AccountResponse(
        @NotNull Long id,
        @NotNull String name,
        @NotNull BigDecimal balance
) {
}
