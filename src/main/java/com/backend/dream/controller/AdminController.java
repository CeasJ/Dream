package com.backend.dream.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AdminController {
        @RequestMapping("/admin")
        public String dashboard(){
            return "/admin/index";
        }
        @RequestMapping("/admin/product")
        public String product(){
            return "/admin/product";
        }
        @RequestMapping("/admin/authority")
        public String authority(){
            return "/admin/authority";
        }
        @RequestMapping("/admin/size")
        public String size(){
            return "/admin/size";
        }
        @RequestMapping("/admin/staff")
        public String staff(){
            return "/admin/staff";
        }

}
