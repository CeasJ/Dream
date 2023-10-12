package com.dream.service;

import com.dream.dto.AccountDTO;
import com.dream.entity.Account;

import java.util.Optional;

public interface AccountService {
    Optional<Account> findByUsername(String username);
}
