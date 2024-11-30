package com.kindgeek.srs.application.accounts.data;

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
