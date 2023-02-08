package com.example.GeekShop.controller;

import lombok.NonNull;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/gender")
public class GendersController {
    @GetMapping
    public String pageOfSelectedGender(@NonNull Model model, Principal principal) {
        model.addAttribute("principal", principal);
        model.addAttribute("nameOfPage", "Choice gender");
        return "/choice_gender";
    }
}
