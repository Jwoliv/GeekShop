package com.example.GeekShop.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class OtherController {
    @GetMapping("/")
    public String homePage() {
        return "Home page";
    }
    @GetMapping("/user")
    public String userPage(Principal principal) {
        return "User page: " + principal.getName();
    }
    @GetMapping("/admin")
    public String adminPage() {
        return "Admin page";
    }
}
