package com.example.GeekShop.controller;

import lombok.NonNull;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class HomeController {
    @GetMapping("/")
    public String AboutController(@NonNull Model model, Principal principal) {
        model.addAttribute("principal", principal);
        model.addAttribute("nameOfPage", "About us");
        return "/about-us";
    }
}
