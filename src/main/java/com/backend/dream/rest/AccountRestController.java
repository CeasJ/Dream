package com.backend.dream.rest;

import com.backend.dream.dto.AccountDTO;
import com.backend.dream.entity.Account;
import com.backend.dream.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profile")
public class AccountRestController {
    @Autowired
    private AccountService accountService;

    @PutMapping("/{username}")
    public AccountDTO updateAccount(@RequestBody AccountDTO accountDTO, @PathVariable("username") String username) {
        return accountService.updateAccount(accountDTO);
    }

}
