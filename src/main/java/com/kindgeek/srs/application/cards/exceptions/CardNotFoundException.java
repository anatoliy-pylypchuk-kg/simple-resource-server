package com.kindgeek.srs.application.cards.exceptions;

public class CardNotFoundException extends RuntimeException {

    public CardNotFoundException(Long id) {
        super("Card with ID %d not found".formatted(id));
    }
}
