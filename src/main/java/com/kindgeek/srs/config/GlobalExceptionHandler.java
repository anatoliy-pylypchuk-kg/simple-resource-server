package com.kindgeek.srs.config;

import com.kindgeek.srs.application.accounts.exceptions.AccountHasCardsException;
import com.kindgeek.srs.application.accounts.exceptions.AccountNotFoundException;
import com.kindgeek.srs.application.cards.exceptions.CardNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.net.URI;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(AccountHasCardsException.class)
    public ProblemDetail handleAccountHasCardsException(AccountHasCardsException e) {
        var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage());
        problemDetail.setType(URI.create("/problems/accounts/has-cards"));
        return problemDetail;
    }

    @ExceptionHandler(AccountNotFoundException.class)
    public ProblemDetail handleAccountNotFoundException(AccountNotFoundException e) {
        var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage());
        problemDetail.setType(URI.create("/problems/accounts/not-found"));
        return problemDetail;
    }

    @ExceptionHandler(CardNotFoundException.class)
    public ProblemDetail handleCardNotFoundException(CardNotFoundException e) {
        var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage());
        problemDetail.setType(URI.create("/problems/cards/not-found"));
        return problemDetail;
    }
}
