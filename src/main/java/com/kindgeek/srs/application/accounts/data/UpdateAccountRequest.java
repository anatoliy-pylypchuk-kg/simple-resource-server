package com.kindgeek.srs.application.accounts.data;

import jakarta.validation.constraints.NotNull;

public record UpdateAccountRequest(@NotNull String name) {
}
