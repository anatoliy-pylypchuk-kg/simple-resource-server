package com.kindgeek.srs.exception;

public class AccountNotFoundException extends RuntimeException {

    public AccountNotFoundException(Long id) {
        super("Account with ID %d not found".formatted(id));
    }
}
