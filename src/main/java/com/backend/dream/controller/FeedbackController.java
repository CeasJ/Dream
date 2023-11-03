//package com.backend.dream.controller;
//
//import com.backend.dream.dto.AccountDTO;
//import com.backend.dream.dto.FeedBackDTO;
//import com.backend.dream.mapper.FeedBackMapper;
//import com.backend.dream.service.AccountService;
//import com.backend.dream.service.FeedbackService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//
//import java.security.Principal;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//@Controller
//public class FeedbackController {
//
//    @Autowired
//    private FeedbackService feedbackService;
//
//    @Autowired
//    private FeedBackMapper feedbackMapper;
//
//    private final AccountService accountService;
//
//    @Autowired
//    public FeedbackController(FeedbackService feedbackService, AccountService accountService) {
//        this.feedbackService = feedbackService;
//        this.accountService = accountService;
//    }
//
//    @GetMapping("/product/{productId}/feedback")
//    public String viewFeedbackSection(@PathVariable Long productId, Model model, Principal principal) {
//        List<FeedBackDTO> feedbacks = feedbackService.getFeedbacksForProduct(productId);
//
//        // Tạo một danh sách chứa thông tin người dùng cho từng bình luận
//        List<AccountDTO> usersInfo = new ArrayList<>();
//
//        // Truy vấn thông tin người dùng cho từng bình luận
//        for (FeedBackDTO feedback : feedbacks) {
//            // Lấy thông tin người dùng dựa trên idaccount của bình luận
//            Optional<AccountDTO> user = accountService.findById(feedback.getIdaccount());
//            if (user.isPresent()) {
//                usersInfo.add(user.get());
//            } else {
//                AccountDTO defaultUser = new AccountDTO();
//                defaultUser.setFullname("Unknown User");
//                usersInfo.add(defaultUser);
//            }
//        }
//
//        model.addAttribute("feedbacks", feedbacks);
//        model.addAttribute("usersInfo", usersInfo);
//
//        boolean isLoggedIn = false;
//        if (principal != null) {
//            isLoggedIn = true;
//        }
//
//        model.addAttribute("isLoggedIn", isLoggedIn);
//
//        FeedBackDTO newFeedback = new FeedBackDTO();
//        model.addAttribute("newFeedback", newFeedback);
//
//        return "user/product/detail";
//    }
//
//
//    @PostMapping("/product/{productId}/feedback")
//    public String addFeedback(@PathVariable Long productId, @ModelAttribute("newFeedback") FeedBackDTO newFeedback, Principal principal) {
//        // Kiểm tra xem người dùng đã đăng nhập hay chưa
//        if (principal != null) {
//            newFeedback.setId_product(productId);
//            feedbackService.save(newFeedback);
//        }
//
//        return "redirect:/product/" + productId + "/feedback";
//    }
//}
//
//
