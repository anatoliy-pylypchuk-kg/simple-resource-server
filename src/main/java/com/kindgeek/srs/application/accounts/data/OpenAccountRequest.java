package com.kindgeek.srs.application.accounts.data;

import jakarta.validation.constraints.NotNull;

public record OpenAccountRequest(@NotNull String name, @NotNull String currency) {
}
