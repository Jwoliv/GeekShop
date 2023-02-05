package com.example.GeekShop.controller.admin;

import com.example.GeekShop.service.product.ProductService;
import jakarta.validation.constraints.NotNull;
import jakarta.websocket.server.PathParam;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.Locale;

@Controller
@RequestMapping("/admin/product")
public class AdminProductsController {
    private final ProductService productService;

    @Autowired
    public AdminProductsController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public String pageOfProducts(@NotNull Model model, Principal principal) {
        model.addAttribute("url", "product");
        model.addAttribute("principal", principal);
        model.addAttribute("title", "Products");
        model.addAttribute("all_elements", productService.findAll());
        return "/admin/all_elements";
    }
    @GetMapping("/find")
    public String findByNameOfProduct(
            @PathParam("name") String name,
            @NonNull Model model,
            Principal principal
    ) {
        model.addAttribute("url", "product");
        model.addAttribute("principal", principal);
        model.addAttribute("title", "Products");
        model.addAttribute("name", name);
        model.addAttribute("all_elements", productService.findProductsByName(name.toUpperCase(Locale.ROOT)));
        return "/admin/all_elements";
    }
}
