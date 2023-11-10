package com.backend.dream.controller;

import com.backend.dream.dto.ProductDTO;
import com.backend.dream.mapper.FeedBackMapper;
import com.backend.dream.service.AccountService;
import com.backend.dream.service.FeedbackService;
import com.backend.dream.service.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Controller
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    @Autowired
    private FeedBackMapper feedbackMapper;


    @Autowired
    private AccountService accountService;

    @Autowired
    private ProductService productService;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    public FeedbackController(FeedbackService feedbackService, AccountService accountService) {
        this.feedbackService = feedbackService;
        this.accountService = accountService;
    }


    @PostMapping("/feedback/{name}")
    public String postFeedback(@PathVariable String name,
                               @RequestParam String comment,
                               @RequestParam int rating) {
        // Check if there is a logged-in user
        String remoteUser = request.getRemoteUser();
        if (remoteUser == null) {
            return "redirect:/product/" + name;
        } else {
            try {
                // Lấy productId từ tên sản phẩm và mã hóa tên sản phẩm thành UTF-8
                String decodedName = URLDecoder.decode(name, "UTF-8");
                String encodedName = URLEncoder.encode(decodedName, StandardCharsets.UTF_8);
                ProductDTO product = productService.findByNamePaged(decodedName, PageRequest.of(0, 1)).getContent().get(0);
                Long productId = product.getId();

                Long accountId = accountService.findIDByUsername(remoteUser);
                feedbackService.createFeedback(productId, accountId, comment, rating);
                return "redirect:/product/" + encodedName;
            } catch (UnsupportedEncodingException e) {
                return "error";
            }
        }
    }





}