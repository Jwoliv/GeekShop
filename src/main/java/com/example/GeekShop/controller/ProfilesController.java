package com.example.GeekShop.controller;

import com.example.GeekShop.model.order.Order;
import com.example.GeekShop.model.user.User;
import com.example.GeekShop.service.product.ProductService;
import com.example.GeekShop.service.user.UserService;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Comparator;

@Controller
@RequestMapping("/profile")
public class ProfilesController {
    private final UserService userService;
    private final ProductService productService;
    @Autowired
    public ProfilesController(
            UserService userService,
            ProductService productService
    ) {
        this.userService = userService;
        this.productService = productService;
    }

    @GetMapping
    public String pageOfProfile(Principal principal, @NonNull Model model) {
        User user = userService.findByEmail(principal.getName());
        user.getOrders().sort(Comparator.comparing(Order::getDateOfCreate).reversed());
        model.addAttribute("user", user);
        model.addAttribute("totalBill", user.getTotalBillOfBasket());
        model.addAttribute("principal", principal);
        return "profile/profile";
    }
    @PostMapping("/{id}/delete_product_from_basket")
    public String deleteProductFromBasket(@PathVariable Long id, Principal principal) {
        productService.deleteProductFromBasketById(principal, id);
        return "redirect:/profile";
    }
    @PostMapping("/{id}/delete_product_from_liked")
    public String deleteProductFromLikedList(@PathVariable Long id, Principal principal) {
        User user = userService.findByEmail(principal.getName());
        user.getListOfLikedProducts().remove(productService.findById(id));
        userService.saveAfterChange(user);
        return "redirect:/profile";
    }
    @GetMapping("/settings")
    public String formSettingsUser(Principal principal, @NonNull Model model) {
        User user = userService.findByEmail(principal.getName());
        model.addAttribute("user", user);
        model.addAttribute("principal", principal);
        return "/profile/settings";
    }
    @PatchMapping("/settings/change_name")
    public String saveUpdatedUser(Principal principal, @RequestParam String firstname, @RequestParam String lastname) {
        if (firstname == null || lastname == null) {
            return "redirect:/profile";
        }
        userService.changeName(principal, lastname, firstname);
        return "redirect:/profile";
    }
    @PatchMapping("/settings/change_password")
    public String saveUserAfterChangePassword(@RequestParam String newPassword, @RequestParam String newPasswordRepeat, Principal principal) {
        User user = userService.findByEmail(principal.getName());
        if (newPassword == null || !newPassword.equals(newPasswordRepeat)) {
            return "redirect:/profile/settings";
        }
        userService.changePassword(user, newPassword);
        return "redirect:/profile";
    }
    @PatchMapping("/settings/change_email")
    public String saveUserAfterChangeEmail(@RequestParam("email") String email, Principal principal) {
        if (email == null) {
            return "redirect:/profile/settings";
        }
        userService.changeEmailForUser(email, principal);
        return "redirect:/login";
    }
    @DeleteMapping
    public String deleteUserAccount(Principal principal) {
        if (userService.checkOrderDoesNotHasCompletedStatus(principal)) {
            return "redirect:/profile/settings";
        }
        productService.cleanUsersBasketOfProduct(principal);
        userService.deleteByEmail(principal.getName());
        return "redirect:/registration";
    }
}
