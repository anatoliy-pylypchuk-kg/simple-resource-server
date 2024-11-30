package com.kindgeek.srs.application.cards;

import com.kindgeek.srs.domain.Card;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CardRepository extends JpaRepository<Card, Long> {

    Page<Card> findAllByAccountOwnerId(UUID ownerId, Pageable pageable);

    boolean existsByIdAndAccountOwnerId(Long id, UUID ownerId);

    boolean existsByAccountId(Long accountId);
}
