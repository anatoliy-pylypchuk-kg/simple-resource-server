package com.kindgeek.srs.application.accounts.exceptions;

public class AccountNotFoundException extends RuntimeException {

    public AccountNotFoundException(Long id) {
        super("Account with ID %d not found".formatted(id));
    }
}
