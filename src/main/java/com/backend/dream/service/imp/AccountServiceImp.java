package com.backend.dream.service.imp;

import com.backend.dream.entity.Account;
import com.backend.dream.repository.AccountRepository;
import com.backend.dream.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class AccountServiceImp implements AccountService {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public Optional<Account> findByUsername(String username) {
        return accountRepository.findByUsername(username);
    }
    @Override
    public Long findIDByUsername(String username) throws NoSuchElementException {
        return accountRepository.findIdByUsername(username);
    }


}
