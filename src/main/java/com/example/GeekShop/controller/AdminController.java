package com.example.GeekShop.controller;

import com.example.GeekShop.service.product.ProductService;
import com.example.GeekShop.service.product_fields.CategoryService;
import com.example.GeekShop.service.product_fields.SeasonService;
import com.example.GeekShop.service.product_fields.ThemeService;
import com.example.GeekShop.service.user.UserService;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final ProductService productService;
    private final CategoryService categoryService;
    private final SeasonService seasonService;
    private final ThemeService themeService;
    private final UserService userService;
    @Autowired
    public AdminController(
            ProductService productService,
            CategoryService categoryService,
            SeasonService seasonService,
            ThemeService themeService,
            UserService userService
    ) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.seasonService = seasonService;
        this.themeService = themeService;
        this.userService = userService;
    }

    @GetMapping
    public String mainPageOfAdmin() {
        return "/admin/admin_main";
    }
    @GetMapping("/product")
    private String pageOfProducts(@NotNull Model model) {
        model.addAttribute("url", "product");
        model.addAttribute("title", "Products");
        model.addAttribute("elements", productService.findAll());
        return "/admin/all_elements";
    }
    @GetMapping("/category")
    public String pageOfCategories(@NotNull Model model) {
        model.addAttribute("url", "category");
        model.addAttribute("title", "Categories");
        model.addAttribute("categories", categoryService.findAll());
        return "/admin/all_elements";
    }
    @GetMapping("/season")
    public String pageOfSeasons(@NotNull Model model) {
        model.addAttribute("url", "season");
        model.addAttribute("title", "Seasons");
        model.addAttribute("elements", seasonService.findAll());
        return "/admin/all_elements";
    }
    @GetMapping("/theme")
    public String pageOfThemes(@NotNull Model model) {
        model.addAttribute("url", "theme");
        model.addAttribute("title", "Themes");
        model.addAttribute("elements", themeService.findAll());
        return "/admin/all_elements";
    }
    @GetMapping("/users")
    public String pageOfUsers(@NotNull Model model) {
        model.addAttribute("users", userService.findAll());
        return "/admin/all_users";
    }
    @GetMapping("/users/{id}")
    public String pageOfSelectedUser(@PathVariable Long id, @NotNull Model model) {
        model.addAttribute("user", userService.findById(id));
        return "/admin/selected_user";
    }
}
