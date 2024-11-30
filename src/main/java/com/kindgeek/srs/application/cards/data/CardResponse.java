package com.kindgeek.srs.application.cards.data;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record CardResponse(
        @NotNull Long id,
        @NotNull String nameOnCard,
        @NotNull String cardNumber,
        @NotNull LocalDate expiryDate
) {
}
