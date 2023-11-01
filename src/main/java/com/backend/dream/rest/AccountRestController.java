package com.backend.dream.rest;

import com.backend.dream.dto.AccountDTO;
import com.backend.dream.entity.Account;
import com.backend.dream.service.AccountService;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequestMapping("/rest/account")
public class AccountRestController {
    @Autowired
    AccountService accountService;
    @Autowired
    HttpServletRequest request;
    @GetMapping("/admin")
    public List<Account> getAccounts(@RequestParam("admin") Optional<Boolean> admin) {
        if (admin.orElse(false)) {
            return accountService.getStaff();
        } else {
            return accountService.findALL();
        }
    }
    @PostMapping("/add")
    public Account createStaff(@RequestBody JsonNode account, Model model) {
        String username = account.get("username").asText();
        return accountService.checkUsernameExists(username) ? null : accountService.createStaff(account);
    }

    @PutMapping("/update/{id}")
    public Account updateStaff(@RequestBody Account staffToUpdate, @PathVariable("id") Long id) {
        return accountService.updateStaff(staffToUpdate);
    }

}
