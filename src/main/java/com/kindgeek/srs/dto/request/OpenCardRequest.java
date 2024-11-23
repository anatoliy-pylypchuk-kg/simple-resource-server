package com.kindgeek.srs.dto.request;

import jakarta.validation.constraints.NotNull;

public record OpenCardRequest(
        @NotNull Long accountId,
        @NotNull String nameOnCard
) {
}
