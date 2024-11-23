package com.kindgeek.srs.exception;

public class CardNotFoundException extends RuntimeException {

    public CardNotFoundException(Long id) {
        super("Card with ID %d not found".formatted(id));
    }
}
