package com.kindgeek.srs.application.accounts;

import com.kindgeek.srs.domain.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Page<Account> findAllByOwnerId(UUID ownerId, Pageable pageable);

    Optional<Account> findByIdAndOwnerId(Long id, UUID ownerId);

    boolean existsByIdAndOwnerId(Long id, UUID ownerId);
}
