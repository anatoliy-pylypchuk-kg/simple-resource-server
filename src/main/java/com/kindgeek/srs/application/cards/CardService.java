package com.kindgeek.srs.application.cards;

import com.kindgeek.srs.application.cards.data.OpenCardRequest;
import com.kindgeek.srs.application.cards.data.CardResponse;
import com.kindgeek.srs.domain.Card;
import com.kindgeek.srs.application.accounts.exceptions.AccountNotFoundException;
import com.kindgeek.srs.application.cards.exceptions.CardNotFoundException;
import com.kindgeek.srs.application.accounts.AccountRepository;
import lombok.RequiredArgsConstructor;
import net.datafaker.providers.base.Finance;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import net.datafaker.Faker;

import java.time.LocalDate;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CardService {

    private final CardRepository cardRepository;
    private final AccountRepository accountRepository;
    private final Faker faker;

    @Transactional(readOnly = true)
    public Page<CardResponse> getCards(UUID userId, Pageable pageable) {
        return cardRepository.findAllByAccountOwnerId(userId, pageable)
                .map(this::mapToResponse);
    }

    @Transactional
    public CardResponse openCard(UUID userId, OpenCardRequest request) {
        var account = accountRepository.findByIdAndOwnerId(request.accountId(), userId)
                .orElseThrow(() -> new AccountNotFoundException(request.accountId()));

        var card = Card.builder()
                .nameOnCard(request.nameOnCard())
                .cardNumber(faker.finance().creditCard(Finance.CreditCardType.MASTERCARD).replace("-", ""))
                .expiryDate(LocalDate.now().plusYears(4))
                .cvv(faker.numerify("###"))
                .account(account)
                .build();

        return mapToResponse(cardRepository.save(card));
    }

    @Transactional
    public void closeCard(UUID userId, Long id) {
        if (!cardRepository.existsByIdAndAccountOwnerId(id, userId)) {
            throw new CardNotFoundException(id);
        }

        cardRepository.deleteById(id);
    }

    private CardResponse mapToResponse(Card card) {
        return CardResponse.builder()
                .id(card.getId())
                .nameOnCard(card.getNameOnCard())
                .cardNumber(card.getCardNumber())
                .expiryDate(card.getExpiryDate())
                .build();
    }
}
