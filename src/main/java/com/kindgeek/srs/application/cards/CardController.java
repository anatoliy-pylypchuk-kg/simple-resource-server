package com.kindgeek.srs.application.cards;

import com.kindgeek.srs.application.cards.data.OpenCardRequest;
import com.kindgeek.srs.application.cards.data.CardResponse;
import com.kindgeek.srs.entity.Card_;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.StandardClaimNames;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/cards")
@Tag(name = "cards")
@RequiredArgsConstructor
public class CardController {

    private final CardService cardService;

    @GetMapping
    @Operation(operationId = "get-cards", summary = "Get cards")
    public Page<CardResponse> getCards(
            @AuthenticationPrincipal Jwt token,
            @SortDefault(Card_.CARD_NUMBER) @ParameterObject Pageable pageable
    ) {
        return cardService.getCards(getUserId(token), pageable);
    }

    @PostMapping
    @Operation(operationId = "open-card", summary = "Open card")
    public CardResponse openCard(
            @AuthenticationPrincipal Jwt token,
            @RequestBody @Valid OpenCardRequest request
    ) {
        return cardService.openCard(getUserId(token), request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(operationId = "close-card", summary = "Close card")
    public void closeCard(@AuthenticationPrincipal Jwt token, @PathVariable Long id) {
        cardService.closeCard(getUserId(token), id);
    }

    private UUID getUserId(Jwt token) {
        return UUID.fromString(token.getClaimAsString(StandardClaimNames.SUB));
    }
}
