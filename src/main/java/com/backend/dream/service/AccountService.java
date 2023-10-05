package com.backend.dream.service;


import com.backend.dream.dto.AccountDTO;
import com.backend.dream.entity.Account;

import java.util.Optional;

public interface AccountService {
    Optional<Account> findByUsername(String username);
    AccountDTO registerAccount(AccountDTO accountDTO);
    public boolean isUsernameExists(String username);

    public boolean isEmailExists(String email);

}

