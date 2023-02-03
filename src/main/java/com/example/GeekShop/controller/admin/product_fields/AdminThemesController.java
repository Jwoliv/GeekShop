package com.example.GeekShop.controller.admin.product_fields;

import com.example.GeekShop.service.product_fields.ThemeService;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminThemesController {
    private final ThemeService themeService;

    @Autowired
    public AdminThemesController(ThemeService themeService) {
        this.themeService = themeService;
    }

    @GetMapping("/theme")
    public String pageOfThemes(@NotNull Model model) {
        model.addAttribute("url", "theme");
        model.addAttribute("title", "Themes");
        model.addAttribute("elements", themeService.findAll());
        return "/admin/all_elements";
    }
}
