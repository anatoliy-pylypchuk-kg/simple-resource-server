package com.kindgeek.srs.application.accounts;

import com.kindgeek.srs.application.accounts.data.OpenAccountRequest;
import com.kindgeek.srs.application.accounts.data.UpdateAccountRequest;
import com.kindgeek.srs.application.accounts.data.AccountResponse;
import com.kindgeek.srs.domain.Account_;
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
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/accounts")
@Tag(name = "accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @GetMapping
    @Operation(operationId = "get-accounts", summary = "Get accounts")
    public Page<AccountResponse> getAccounts(
            @AuthenticationPrincipal Jwt token,
            @SortDefault(Account_.NAME) @ParameterObject Pageable pageable
    ) {
        return accountService.getAccounts(getUserId(token), pageable);
    }

    @PostMapping
    @Operation(operationId = "open-account", summary = "Open account")
    public AccountResponse openAccount(
            @AuthenticationPrincipal Jwt token,
            @RequestBody @Valid OpenAccountRequest request
    ) {
        return accountService.openAccount(getUserId(token), request);
    }

    @PatchMapping("/{id}")
    @Operation(operationId = "update-account", summary = "Update account")
    public AccountResponse updateAccount(
            @AuthenticationPrincipal Jwt token,
            @PathVariable Long id,
            @RequestBody @Valid UpdateAccountRequest request
    ) {
        return accountService.updateAccount(getUserId(token), id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(operationId = "close-account", summary = "Close account")
    public void closeAccount(@AuthenticationPrincipal Jwt token, @PathVariable Long id) {
        accountService.closeAccount(getUserId(token), id);
    }

    private UUID getUserId(Jwt token) {
        return UUID.fromString(token.getClaimAsString(StandardClaimNames.SUB));
    }
}
