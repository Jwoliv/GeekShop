package com.example.GeekShop.controller.admin.product_fields;

import com.example.GeekShop.service.product_fields.CategoryService;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminCategoriesController {
    private final CategoryService categoryService;

    @Autowired
    public AdminCategoriesController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/category")
    public String pageOfCategories(@NotNull Model model) {
        model.addAttribute("url", "category");
        model.addAttribute("title", "Categories");
        model.addAttribute("elements", categoryService.findAll());
        return "/admin/all_elements";
    }
}
