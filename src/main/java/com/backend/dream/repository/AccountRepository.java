package com.backend.dream.repository;

import com.backend.dream.dto.AccountDTO;
import com.backend.dream.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account,Long> {
    Optional<Account> findByUsername(String username);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);

    Account findByUsernameAndEmail(String username, String email);
}
