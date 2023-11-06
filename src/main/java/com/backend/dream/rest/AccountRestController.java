package com.backend.dream.rest;

import com.backend.dream.dto.AccountDTO;
import com.backend.dream.dto.AccountDTO;
import com.backend.dream.entity.Account;
import com.backend.dream.mapper.AccountMapper;
import com.backend.dream.service.AccountService;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.NoSuchElementException;

@CrossOrigin("*")
@RestController
@RequestMapping("/rest/profile")
public class AccountRestController {
    @Autowired
    private AccountService accountService;
    @Autowired
    private AccountMapper accountMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

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

<<<<<<< HEAD
    @PutMapping("/update/{id}")
    public Account updateStaff(@RequestBody Account staffToUpdate, @PathVariable("id") Long id) {
        return accountService.updateStaff(staffToUpdate);
=======
    @PostMapping("/authenticate/{id}")
    public boolean authenticate(@PathVariable("id") Long id, @RequestBody Map<String, String> body) {
        String password = body.get("password");
        AccountDTO accountDTO = accountService.findById(id);
        return passwordEncoder.matches(password, accountDTO.getPassword());
    }

    @PutMapping("/changePassword/{id}")
    public ResponseEntity<AccountDTO> updatePassword(@PathVariable("id") Long id,
            @RequestBody Map<String, String> requestBody) {
        String newPassword = requestBody.get("password");
        AccountDTO accountDTO = accountService.findById(id);
        accountService.updatePassword(accountDTO, newPassword);
        return new ResponseEntity<>(accountDTO, HttpStatus.OK);
>>>>>>> cuong
    }

}
