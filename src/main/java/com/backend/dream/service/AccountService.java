package com.backend.dream.service;

import com.backend.dream.dto.AccountDTO;
import com.backend.dream.entity.Account;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public interface AccountService {
    Optional<Account> findByUsername(String username);

    AccountDTO registerAccount(AccountDTO accountDTO);

    public boolean isUsernameExists(String username);

    public boolean isEmailExists(String email);

    Account create(Account account);

    Account findByUsernameAndEmail(String username, String email);

    Account updatePassword(Account account, String password);

    Long findIDByUsername(String username) throws NoSuchElementException;

    String findFullNameByUsername(String username) throws NoSuchElementException;

    List<Account> getStaff();

    List<Account> findALL();

    Account findById(String username);

    String getImageByUserName(String remoteUser) throws NoSuchElementException;
}
