package com.example.GeekShop.controller;

import com.example.GeekShop.service.product.ProductService;
import com.example.GeekShop.service.user.UserService;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.example.GeekShop.model.user.User;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
public class UsersController {
    private final UserService userService;
    private final ProductService productService;
    @Autowired
    public UsersController(UserService userService, ProductService productService) {
        this.userService = userService;
        this.productService = productService;
    }

    @GetMapping("/profile")
    public String pageOfProfile(Principal principal, @NonNull Model model) {
        User user = userService.findByEmail(principal.getName());
        model.addAttribute("user", user);
        model.addAttribute("totalBill", user.getTotalBillOfBasket());
        model.addAttribute("principal", principal);
        return "/profile";
    }
    @PostMapping("/profile/{id}/delete_product_from_basket")
    public String deleteProductFromBasket(
            @PathVariable Long id,
            Principal principal
    ) {
        User user = userService.findByEmail(principal.getName());
        user.getBasketOfProducts().remove(productService.findById(id));
        userService.saveAfterChange(user);
        return "redirect:/profile";
    }
    @PostMapping("profile/{id}/delete_product_from_liked")
    public String deleteProductFromLikedList(
            @PathVariable Long id,
            Principal principal
    ) {
        User user = userService.findByEmail(principal.getName());
        user.getListOfLikedProducts().remove(productService.findById(id));
        userService.saveAfterChange(user);
        return "redirect:/profile";
    }
}
