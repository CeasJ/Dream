//package com.dream.controller;
//
//import com.backend.dream.dto.AccountDTO;
//import com.backend.dream.service.AccountService;
//import jakarta.validation.Valid;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
//@Controller
//public class RegisterController {
//
//    @Autowired
//    private AccountService accountService;
//
//    @GetMapping("/register")
//    public String showRegistrationForm(AccountDTO accountDTO) {
//        return "/user/register"; // Trả về giao diện người dùng Thymeleaf để hiển thị biểu mẫu đăng ký.
//    }
//
//    @PostMapping("/register")
//    public String registerAccount(@RequestParam("password") String password,@RequestParam("confirmPassword") String confirmPassword,
//                                  @Valid AccountDTO accountDTO, BindingResult bindingResult,Model model) {
//        String username = accountDTO.getUsername();
//        String email = accountDTO.getEmail();
//        // Kiểm tra xem username đã tồn tại
//        if (accountService.isUsernameExists(username)) {
//            model.addAttribute("error", "Username valid");
//            return "/user/register";
//        }
////        if (accountService.isEmailExists(email)) {
////            model.addAttribute("error", "Email valid");
////            return "/user/register";
////        }
//        if (bindingResult.hasErrors()) {
//            model.addAttribute("message", "Please review registration information");
//            model.addAttribute("accountDTO", accountDTO);
//            return "/user/register";
//        }
//        if (password.equals(confirmPassword)) {
//            accountService.registerAccount(accountDTO);
//            return "redirect:/user/login";
//        } else {
//            model.addAttribute("error", "Wrong Password");
//            return "/user/register";
//        }
//    }
//}
