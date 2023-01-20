package com.example.GeekShop.controller;

import com.example.GeekShop.model.user.User;
import com.example.GeekShop.service.user.UserService;
import jakarta.validation.Valid;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Objects;

@Controller
public class AuthenticationController {
    private final UserService userService;

    @Autowired
    public AuthenticationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String login() {
        return "/login";
    }

    @GetMapping("/registration")
    public String registration(@NonNull Model model) {
        model.addAttribute("user", new User());
        return "/registration";
    }

    @PostMapping("/registration")
    public String registerProcessing(
            @ModelAttribute @Valid User user,
            BindingResult bindingResult,
            @NonNull Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("user", new User());
            return "/registration";
        }
        else if (!Objects.equals(user.getPassword(), user.getRepeatPassword()) || userService.findByEmail(user.getEmail()) != null) {
            model.addAttribute("user", new User());
            model.addAttribute("error-email", "User with so email has already exist");
            model.addAttribute("error-password", "Passwords aren't equals");
            return "/registration";
        }
        userService.save(user);
        return "redirect:/login";
    }
}
