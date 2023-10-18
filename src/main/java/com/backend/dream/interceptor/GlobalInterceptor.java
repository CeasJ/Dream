package com.backend.dream.interceptor;

import com.backend.dream.service.CategoryService;
import com.backend.dream.service.ProductService;
import com.backend.dream.service.ProductSizeService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class GlobalInterceptor implements HandlerInterceptor {
    @Autowired
    CategoryService categoryService;

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductSizeService productSizeService;

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        request.setAttribute("categories", categoryService.findAll());

    }
}
