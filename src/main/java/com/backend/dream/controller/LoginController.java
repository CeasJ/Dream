package com.backend.dream.controller;

import com.backend.dream.entity.Account;
import com.backend.dream.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class LoginController {
    @Autowired
    private AccountService accountService;

    @GetMapping("/form")
    public String formLogin(){
        return "loginForm";
    }
    @RequestMapping("/demo")
    public String testUI(){
        return "test";
    }
    @RequestMapping("/admin/index")
    public String adminIndex(){
        return "admin";
    }
    @RequestMapping("/staff/index")
    public String staffIndex(){
        return "staff";
    }
    @RequestMapping("/products/index")
    public String products(){
        return "products";
    }
    @RequestMapping("/login")
    public String login(@RequestParam("username") String username, @RequestParam("password") String password){
        Optional<Account> account =  accountService.findByUsername(username);
        if (account.isPresent()){
            return "redirect:/products/index";
        } else {
            return "redirect:/auth/error";
        }
    }
    @RequestMapping("/auth/error")
    public String error(){
        return "/form";
    }
}
