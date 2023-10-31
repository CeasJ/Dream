package com.backend.dream.rest;

import com.backend.dream.dto.AccountDTO;
import com.backend.dream.entity.Account;
import com.backend.dream.mapper.AccountMapper;
import com.backend.dream.service.AccountService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;
@CrossOrigin("*")
@RestController
@RequestMapping("/rest/profile")
public class AccountRestController {
    @Autowired
    private AccountService accountService;
    @Autowired
    private AccountMapper accountMapper;

    @GetMapping("/{id}")
    public ResponseEntity<AccountDTO> getOne(@PathVariable("id") Long id) {
        try {
            AccountDTO accountDTO = accountService.findById(id);
            if (accountDTO != null) {
                return new ResponseEntity<>(accountDTO, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<AccountDTO> update(@RequestBody AccountDTO accountDTO, @PathVariable("id") Long id) {
        try {
            AccountDTO updatedAccountDTO = accountService.updateAccount(accountDTO);
            if (updatedAccountDTO != null) {
                return new ResponseEntity<>(updatedAccountDTO, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

