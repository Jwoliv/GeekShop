package com.example.GeekShop.controller;

import com.example.GeekShop.model.order.Order;
import com.example.GeekShop.model.order.StatusOfOrder;
import com.example.GeekShop.model.product.Product;
import com.example.GeekShop.model.product.ProductByBasket;
import com.example.GeekShop.model.user.User;
import com.example.GeekShop.service.product.ProductByBasketService;
import com.example.GeekShop.service.product.ProductService;
import com.example.GeekShop.service.user.UserService;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/profile")
public class ProfilesController {
    private final UserService userService;
    private final ProductService productService;
    private final ProductByBasketService productByBasketService;
    private final BCryptPasswordEncoder passwordEncoder;
    @Autowired
    public ProfilesController(
            UserService userService,
            ProductService productService,
            ProductByBasketService productByBasketService,
            BCryptPasswordEncoder passwordEncoder
    ) {
        this.userService = userService;
        this.productService = productService;
        this.productByBasketService = productByBasketService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public String pageOfProfile(Principal principal, @NonNull Model model) {
        User user = userService.findByEmail(principal.getName());
        user.getOrders().sort((x1, x2) -> x2.getDateOfCreate().compareTo(x1.getDateOfCreate()));
        model.addAttribute("user", user);
        model.addAttribute("totalBill", user.getTotalBillOfBasket());
        model.addAttribute("principal", principal);
        return "profile/profile";
    }
    @PostMapping("/{id}/delete_product_from_basket")
    public String deleteProductFromBasket(
            @PathVariable Long id,
            Principal principal
    ) {
        User user = userService.findByEmail(principal.getName());
        ProductByBasket productByBasket = productByBasketService.findById(id);
        Product product = productService.findById(productByBasket.getProduct().getId());
        product.setNumberProduct(product.getNumberProduct() + productByBasket.getNumberProduct());
        user.getBasketOfProducts().remove(productByBasket);
        productByBasketService.deleteById(id);
        userService.saveAfterChange(user);
        return "redirect:/profile";
    }
    @PostMapping("/{id}/delete_product_from_liked")
    public String deleteProductFromLikedList(
            @PathVariable Long id,
            Principal principal
    ) {
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
    public String saveUpdatedUser(
            Principal principal,
            @RequestParam String firstname,
            @RequestParam String lastname
    ) {
        if (firstname == null || lastname == null) {
            return "redirect:/profile";
        }
        User user = userService.findByEmail(principal.getName());
        user.setLastname(lastname);
        user.setFirstname(firstname);
        userService.saveAfterChange(user);
        return "redirect:/profile";
    }
    @PatchMapping("/settings/change_password")
    public String saveUserAfterChangePassword(
            @RequestParam String newPassword,
            @RequestParam String newPasswordRepeat,
            Principal principal
    ) {
        User user = userService.findByEmail(principal.getName());
        if (newPassword == null || !newPassword.equals(newPasswordRepeat)) {
            return "redirect:/profile/settings";
        }
        user.setPassword(passwordEncoder.encode(newPassword));
        userService.saveAfterChange(user);
        return "redirect:/profile";
    }
    @PatchMapping("/settings/change_email")
    public String saveUserAfterChangeEmail(@RequestParam("email") String email, Principal principal) {
        if (email == null) {
            return "redirect:/profile/settings";
        }
        changeEmailForUser(email, principal);

        return "redirect:/login";
    }
    @DeleteMapping
    public String deleteUserAccount(Principal principal) {
        if (checkOrderDoesNotHasCompletedStatus(principal)) {
            return "redirect:/profile/settings";
        }
        cleanBasketOfProduct(principal);
        userService.deleteByEmail(principal.getName());
        return "redirect:/registration";
    }
    public void changeEmailForUser(String email, Principal principal) {
        User user = userService.findByEmail(principal.getName());
        user.setEmail(email);
        for (Order order: user.getOrders()) {
            order.setEmail(email);
        }
        userService.saveAfterChange(user);
    }
    public Boolean checkOrderDoesNotHasCompletedStatus(Principal principal) {
        User user = userService.findByEmail(principal.getName());
        if (user.getOrders() == null) return false;
        for (Order orderFromList: user.getOrders()) {
            if (!orderFromList.getStatusOfOrder().equals(StatusOfOrder.Completed)) return true;
        }
        return false;
    }
    public void cleanBasketOfProduct(Principal principal) {
        User user = userService.findByEmail(principal.getName());
        for (ProductByBasket product: user.getBasketOfProducts()) {
            product.getProduct().setNumberProduct(product.getNumberProduct() + product.getProduct().getNumberProduct());
            user.getBasketOfProducts().remove(product);
            productService.save(product.getProduct());
        }
    }
}
