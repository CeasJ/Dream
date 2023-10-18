package com.backend.dream.service;

import com.backend.dream.entity.Account;

import java.util.NoSuchElementException;
import java.util.Optional;

public interface AccountService {
    Optional<Account> findByUsername(String username);

    Long findIDByUsername(String username) throws NoSuchElementException;
}
