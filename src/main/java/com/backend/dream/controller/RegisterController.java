package com.backend.dream.controller;

import com.backend.dream.dto.AccountDTO;
import com.backend.dream.service.AccountService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegisterController {

    @Autowired
    private AccountService accountService;

    @GetMapping("/register")
    public String showRegistrationForm(AccountDTO accountDTO) {
        return "/user/register"; // Trả về giao diện người dùng Thymeleaf để hiển thị biểu mẫu đăng ký.
    }

    @PostMapping("/register")
    public String registerAccount(@Valid AccountDTO accountDTO, BindingResult bindingResult, Model model) {
        String username = accountDTO.getUsername();

        // Kiểm tra xem username đã tồn tại
        if (accountService.isUsernameExists(username)) {
            model.addAttribute("error", "Username valid");
            return "/user/register";
        }
        if (bindingResult.hasErrors()) {
            model.addAttribute("message", "Please review registration information");
            model.addAttribute("accountDTO", accountDTO);
            return "/user/register";
        }
        // Xử lý đăng ký tài khoản ở đây, sử dụng AccountService.
        accountService.registerAccount(accountDTO);
        // Thực hiện chuyển hướng hoặc hiển thị thông báo thành công.
        return "redirect:/user/login"; // Chuyển hướng sau khi đăng ký thành công.
    }
}
