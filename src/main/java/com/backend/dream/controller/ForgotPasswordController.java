package com.backend.dream.controller;

import com.backend.dream.config.EmailService;
import com.backend.dream.dto.TokenDTO;
import com.backend.dream.entity.Account;
import com.backend.dream.entity.Token;
import com.backend.dream.repository.TokenRepository;
import com.backend.dream.service.AccountService;
import com.backend.dream.service.TokenService;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;

@Controller
public class ForgotPasswordController {
    @Autowired
    AccountService accountService;
    @Autowired
    TokenService tokenService;
    @Autowired
    EmailService emailService;
    @Autowired
    private TokenRepository tokenRepository;

    @GetMapping("/forgot")
    public String showForgotPasswordForm() {
        return "/user/security/forgotPass";
    }

    @PostMapping("/forgot")
    public String processForgotPasswordForm(@RequestParam("username") String username,
            @RequestParam("email") String email, Model model, HttpSession session) {

        Account account = accountService.findByUsernameAndEmail(username, email);
        if (account == null) {
            model.addAttribute("message", "Invalid username or email");
            return "/user/security/forgotPass";
        }
        TokenDTO token = tokenService.createTokenForUser(account);
        String tokenValue = token.getToken();
        session.setAttribute("tokenValue", tokenValue);
        emailService.sendEmailTokenPass(email, String.valueOf(tokenValue), account.getFullname());
        model.addAttribute("message", "An email with a reset link has been sent to your email address");
        return "/user/security/verifi";
    }

    @GetMapping("/verifi")
    public String showVerifiForm() {
        return "/user/security/verifi";
    }

    @PostMapping("/verifi")
    public String processVerifiForm(@RequestParam("token") String tokenveri, Model model, HttpSession session) {
        String tokenValue = (String) session.getAttribute("tokenValue");
        if (tokenveri.equals(tokenValue)) {
            Token token = tokenService.findByToken(tokenValue);
            LocalDateTime expiryDate = token.getExpiredDate();
            if (LocalDateTime.now().isBefore(expiryDate)) {
                return "redirect:/confirmPass";
            } else {
                delete();
                model.addAttribute("message", "Expired token");
                return "/user/security/verifi";
            }
        } else {
            model.addAttribute("message", "Invalid token");
            return "/user/security/verifi";
        }
    }

    @GetMapping("/confirmPass")
    public String confirmPassForm(Model model) {
        return "/user/security/confirmPass";
    }

    @PostMapping("/confirmPass")
    public String processResetPassword(@RequestParam("password") String password,
            @RequestParam("confirmPassword") String confirmPassword,
            Model model, HttpSession session) {
        String tokenValue = (String) session.getAttribute("tokenValue");
        Token token = tokenService.findByToken(tokenValue);
        Account account = token.getAccount();

        if (!password.equals(confirmPassword)) {
            model.addAttribute("message", "Passwords do not match");
            return "/user/security/confirmPass";
        }
        accountService.updatePassword(account, password);

        delete();

        model.addAttribute("message", "Your password has been reset successfully");
        return "/user/security/login";
    }

    @Transactional
    public void delete() {
        LocalDateTime now = LocalDateTime.now().plusMinutes(1);
        tokenRepository.deleteByExpiredDateBefore(now);
    }
}
