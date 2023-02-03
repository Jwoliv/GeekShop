package com.example.GeekShop.controller.admin;

import com.example.GeekShop.service.product.ProductService;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminProductsController {
    private final ProductService productService;

    @Autowired
    public AdminProductsController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/product")
    private String pageOfProducts(@NotNull Model model) {
        model.addAttribute("url", "product");
        model.addAttribute("title", "Products");
        model.addAttribute("elements", productService.findAll());
        return "/admin/all_elements";
    }
}
