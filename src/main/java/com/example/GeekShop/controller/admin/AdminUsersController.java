package com.example.GeekShop.controller.admin;

import com.example.GeekShop.model.user.User;
import com.example.GeekShop.service.user.UserService;
import jakarta.validation.constraints.NotNull;
import jakarta.websocket.server.PathParam;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.Locale;

@Controller
@RequestMapping("/admin/user")
public class AdminUsersController {
    private final UserService userService;

    @Autowired
    public AdminUsersController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String pageOfUsers(@NotNull Model model, Principal principal) {
        model.addAttribute("users", userService.findAll());
        model.addAttribute("principal", principal);
        return "/admin/all_users";
    }
    @GetMapping("/{id}")
    public String pageOfSelectedUser(@PathVariable Long id, @NotNull Model model, Principal principal) {
        User user = userService.findById(id);
        model.addAttribute("user", user);
        model.addAttribute("totalBill", user.getTotalBillOfBasket());
        model.addAttribute("principal", principal);
        return "/admin/selected_user";
    }
    @GetMapping("/find")
    public String findProductsByName(@PathParam("name") String name, @NonNull Model model, Principal principal) {
        model.addAttribute("users", userService.findByName(name.toUpperCase(Locale.ROOT)));
        model.addAttribute("principal", principal);
        model.addAttribute("find_name", name);
        return "/admin/all_users";
    }
}
