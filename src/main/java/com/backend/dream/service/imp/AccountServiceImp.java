package com.backend.dream.service.imp;

import com.backend.dream.dto.AccountDTO;
import com.backend.dream.entity.Account;
import com.backend.dream.mapper.AccountMapper;
import com.backend.dream.repository.AccountRepository;
import com.backend.dream.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountServiceImp implements AccountService {

    @Autowired
    AccountMapper accountMapper;
    @Autowired
    private AccountRepository accountRepository;

//    @Bean
//    public PasswordEncoder passwordEncoder(){
//        return new BCryptPasswordEncoder();
//    }
    @Override
    public Optional<Account> findByUsername(String username) {
        return accountRepository.findByUsername(username);
    }

    @Override
    public AccountDTO registerAccount(AccountDTO accountDTO) {
        Account account = accountMapper.accountDTOToAccount(accountDTO);
        account.setFullname(accountDTO.getFirstname() + " " + accountDTO.getLastname());
//        account.setPassword(passwordEncoder().encode(accountDTO.getPassword()));
        Account saveAccount = accountRepository.save(account);
        return accountMapper.accountToAccountDTO(saveAccount);
    }

    @Override
    public boolean isUsernameExists(String username) {
        return accountRepository.existsByUsername(username);
    }


}
