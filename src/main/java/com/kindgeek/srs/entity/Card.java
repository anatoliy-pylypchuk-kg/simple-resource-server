package com.kindgeek.srs.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "card")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "name_on_card", nullable = false)
    private String nameOnCard;

    @NotNull
    @Column(name = "card_number", nullable = false, unique = true)
    private String cardNumber;

    @NotNull
    @Column(name = "expiry_date", nullable = false)
    private LocalDate expiryDate;

    @NotNull
    @Column(name = "cvv", nullable = false)
    private String cvv;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;
}
