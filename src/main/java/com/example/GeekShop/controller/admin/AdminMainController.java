package com.example.GeekShop.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminMainController {
    @GetMapping
    public String mainPageOfAdmin() {
        return "/admin/admin_main";
    }
}
