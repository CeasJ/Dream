package com.backend.dream.service.imp;

import com.backend.dream.dto.AccountDTO;
import com.backend.dream.entity.Account;
import com.backend.dream.entity.Product;
import com.backend.dream.mapper.AccountMapper;
import com.backend.dream.repository.AccountRepository;
import com.backend.dream.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class AccountServiceImp implements AccountService {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AccountMapper accountMapper;

    @Override
    public Optional<Account> findByUsername(String username) {
        return accountRepository.findByUsername(username);
    }
    @Override
    public AccountDTO registerAccount(AccountDTO accountDTO) {
        Account account = accountMapper.accountDTOToAccount(accountDTO);
        account.setFullname(accountDTO.getFirstname() + " " + accountDTO.getLastname());
        account.setPassword(passwordEncoder.encode(accountDTO.getPassword()));
        Account saveAccount = accountRepository.save(account);
        return accountMapper.accountToAccountDTO(saveAccount);
    }

    @Override
    public boolean isUsernameExists(String username) {
        return accountRepository.existsByUsername(username);
    }

    @Override
    public boolean isEmailExists(String email) {
        return accountRepository.existsByEmail(email);
    }

    @Override
    public AccountDTO updateAccount(AccountDTO accountDTO) {
        Optional<Account> existingAccountOptional = accountRepository.findByUsername(accountDTO.getUsername());

        if (existingAccountOptional.isPresent()) {
            // Cập nhật thông tin hồ sơ từ DTO
            Account existingAccount = existingAccountOptional.get();
            existingAccount.setFirstname(accountDTO.getFirstname());
            existingAccount.setLastname(accountDTO.getLastname());
            existingAccount.setFullname(accountDTO.getFirstname() + " " + accountDTO.getLastname());
            // Cập nhật tài khoản
            Account updatedAccount = accountRepository.save(existingAccount);
            return accountMapper.accountToAccountDTO(updatedAccount);
        } else {
            throw new NoSuchElementException("Không tìm thấy tài khoản với username: " + accountDTO.getUsername());
        }
    }

    public Long findIDByUsername(String username) throws NoSuchElementException {
        return accountRepository.findIdByUsername(username);
    }

    @Override
    public Account findByUsernameAndEmail(String username, String email) {
        return accountRepository.findByUsernameAndEmail(username, email);
    }

    @Override
    public Account updatePassword(Account account, String password) {
        account.setPassword(passwordEncoder.encode(password));
        accountRepository.save(account);
        return account;
    }

}
