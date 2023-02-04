package com.example.GeekShop.controller.admin;

import lombok.NonNull;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/admin")
public class AdminMainController {
    @GetMapping
    public String mainPageOfAdmin(Principal principal, @NonNull Model model) {
        model.addAttribute("principal", principal);
        return "/admin/admin_main";
    }
}
