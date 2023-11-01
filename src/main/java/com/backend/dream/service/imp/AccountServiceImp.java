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
        account.setAvatar("testimonial-0.jpg");
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
        Account account = accountMapper.accountDTOToAccount(accountDTO);
        account.setFullname(accountDTO.getFirstname() + " " + accountDTO.getLastname());
        Account updatedAccount = accountRepository.save(account);
        return accountMapper.accountToAccountDTO(updatedAccount);
    }


    public Long findIDByUsername(String username) throws NoSuchElementException {
        return accountRepository.findIdByUsername(username);
    }

    @Override
    public AccountDTO findById(Long id) {
        Optional<Account> accountOptional = accountRepository.findById(id);
        return accountOptional.isPresent() ? accountMapper.accountToAccountDTO(accountOptional.get()) : null;
    }


    @Override
    public String findFullNameByUsername(String username) throws NoSuchElementException {
        return accountRepository.findFullNameByUsername(username);
    }
    @Override
    public String getImageByUserName(String remoteUser) throws NoSuchElementException {
        return accountRepository.getImageByUsername(remoteUser);
    }

    @Override
    public Account findByUsernameAndEmail(String username, String email) {
        return accountRepository.findByUsernameAndEmail(username, email);
    }

    @Override
    public AccountDTO updatePassword(AccountDTO accountDTO, String password) {
        Account account = accountMapper.accountDTOToAccount(accountDTO);
        account.setPassword(passwordEncoder.encode(password));
        Account updatedAccount = accountRepository.save(account);
        return accountMapper.accountToAccountDTO(updatedAccount);
    }
    @Override
    public List<Account> getStaff() {
        return accountRepository.getStaff();
    }

    @Override
    public List<Account> findALL() {
        return accountRepository.findAll();
    }

    @Override
    public Account findById(String username) {
        return accountRepository.findById(Long.valueOf(username)).get();
    }

}
