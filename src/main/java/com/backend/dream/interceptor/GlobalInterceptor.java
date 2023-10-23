package com.backend.dream.interceptor;

import com.backend.dream.service.AccountService;
import com.backend.dream.service.CategoryService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class GlobalInterceptor implements HandlerInterceptor {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private AccountService accountService;

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {
        request.setAttribute("categories", categoryService.findAll());
        String remoteUser = request.getRemoteUser();
        Long id_account = accountService.findIDByUsername(remoteUser);
        if (modelAndView != null) {
            if (remoteUser != null && (request.isUserInRole("ADMIN") || request.isUserInRole("STAFF"))) {
                modelAndView.addObject("isAuthenticated", true);
                modelAndView.addObject("isAdminOrStaff", true);
                modelAndView.addObject("username", remoteUser);
                modelAndView.addObject("id_account", id_account);
            } else if (remoteUser != null) {
                modelAndView.addObject("username", remoteUser);
                modelAndView.addObject("id_account", id_account);
                modelAndView.addObject("isAuthenticated", true);
            } else {
                modelAndView.addObject("isAuthenticated", false);
                modelAndView.addObject("isAdminOrStaff", false);
            }
        }
    }
}
