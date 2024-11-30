package com.kindgeek.srs.application.accounts;

import com.kindgeek.srs.application.accounts.data.OpenAccountRequest;
import com.kindgeek.srs.application.accounts.data.UpdateAccountRequest;
import com.kindgeek.srs.application.accounts.data.AccountResponse;
import com.kindgeek.srs.domain.Account;
import com.kindgeek.srs.application.accounts.exceptions.AccountHasCardsException;
import com.kindgeek.srs.application.accounts.exceptions.AccountNotFoundException;
import com.kindgeek.srs.application.cards.CardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final CardRepository cardRepository;

    @Transactional(readOnly = true)
    public Page<AccountResponse> getAccounts(UUID userId, Pageable pageable) {
        return accountRepository.findAllByOwnerId(userId, pageable)
                .map(this::mapToResponse);
    }

    @Transactional
    public AccountResponse openAccount(UUID userId, OpenAccountRequest request) {
        var account = Account.builder()
                .name(request.name())
                .ownerId(userId)
                .balance(BigDecimal.ZERO)
                .build();

        return mapToResponse(accountRepository.save(account));
    }

    @Transactional
    public AccountResponse updateAccount(UUID userId, Long id, UpdateAccountRequest request) {
        var account = accountRepository.findByIdAndOwnerId(id, userId)
                .orElseThrow(() -> new AccountNotFoundException(id));

        account.setName(request.name());
        return mapToResponse(accountRepository.save(account));
    }

    @Transactional
    public void closeAccount(UUID userId, Long id) {
        if (!accountRepository.existsByIdAndOwnerId(id, userId)) {
            throw new AccountNotFoundException(id);
        }

        if (cardRepository.existsByAccountId(id)) {
            throw new AccountHasCardsException(id);
        }

        accountRepository.deleteById(id);
    }

    private AccountResponse mapToResponse(Account account) {
        return AccountResponse.builder()
                .id(account.getId())
                .name(account.getName())
                .balance(account.getBalance())
                .build();
    }
}
