package com.kindgeek.srs.application.cards.data;

import jakarta.validation.constraints.NotNull;

public record OpenCardRequest(
        @NotNull Long accountId,
        @NotNull String nameOnCard
) {
}
