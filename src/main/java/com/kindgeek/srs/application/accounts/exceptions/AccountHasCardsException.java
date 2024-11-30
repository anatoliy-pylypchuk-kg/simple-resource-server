package com.kindgeek.srs.application.accounts.exceptions;

public class AccountHasCardsException extends RuntimeException {

    public AccountHasCardsException(Long id) {
        super("Account with ID %d cannot be deleted as it has active cards".formatted(id));
    }
}
