package com.kindgeek.srs.repository;

import com.kindgeek.srs.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
