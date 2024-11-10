package com.kindgeek.srs.repository;

import com.kindgeek.srs.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<Card, Long> {
}
