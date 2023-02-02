package com.example.GeekShop.controller;

import com.example.GeekShop.model.message.Message;
import com.example.GeekShop.model.order.Order;
import com.example.GeekShop.model.order.StatusOfOrder;
import com.example.GeekShop.model.user.User;
import com.example.GeekShop.service.MessageService;
import com.example.GeekShop.service.OrderService;
import com.example.GeekShop.service.product.ProductService;
import com.example.GeekShop.service.product_fields.CategoryService;
import com.example.GeekShop.service.product_fields.SeasonService;
import com.example.GeekShop.service.product_fields.ThemeService;
import com.example.GeekShop.service.user.UserService;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final ProductService productService;
    private final CategoryService categoryService;
    private final SeasonService seasonService;
    private final ThemeService themeService;
    private final UserService userService;
    private final MessageService messageService;
    private final OrderService orderService;
    @Autowired
    public AdminController(
            ProductService productService,
            CategoryService categoryService,
            SeasonService seasonService,
            ThemeService themeService,
            UserService userService,
            MessageService messageService,
            OrderService orderService
    ) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.seasonService = seasonService;
        this.themeService = themeService;
        this.userService = userService;
        this.messageService = messageService;
        this.orderService = orderService;
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
    @GetMapping("/order")
    public String pageOfAllOrders(@NotNull Model model) {
        model.addAttribute("orders", orderService.findAll());
        return "admin/all_orders";
    }
    @GetMapping("order/{id}")
    public String pageOfSelectedOrder(@NotNull Model model, @PathVariable Long id) {
        model.addAttribute("order", orderService.findById(id));
        return "admin/selected_order";
    }
    @PatchMapping("/order/{id}/change_status")
    public String changeStatusOfOrder(
            @PathVariable Long id,
            @RequestParam StatusOfOrder status
    ) {
        Order order = orderService.findById(id);
        order.setStatusOfOrder(status);
        orderService.save(order);
        if (status == StatusOfOrder.Completed) {
                User user = order.getUser();
                user.setSpendMoney(user.getSpendMoney() + order.getPriceOfOrder());
                user.setBonusPoints(user.getSpendMoney() / 100);
                userService.saveAfterChange(user);
        }
        return "redirect:/admin/order/{id}";
    }
    @GetMapping("/theme")
    public String pageOfThemes(@NotNull Model model) {
        model.addAttribute("url", "theme");
        model.addAttribute("title", "Themes");
        model.addAttribute("elements", themeService.findAll());
        return "/admin/all_elements";
    }
    @GetMapping("/user")
    public String pageOfUsers(@NotNull Model model) {
        model.addAttribute("users", userService.findAll());
        return "/admin/all_users";
    }
    @GetMapping("/user/{id}")
    public String pageOfSelectedUser(@PathVariable Long id, @NotNull Model model) {
        model.addAttribute("user", userService.findById(id));
        return "/admin/selected_user";
    }
    @GetMapping("/message")
    public String pageOfMessages(@NotNull Model model) {
        model.addAttribute("messages", messageService.findAll());
        return "/admin/all_message";
    }
    @PatchMapping("message/{id}")
    public String updateMessage(@PathVariable Long id, @RequestParam String adminAnswer) {
        Message message = messageService.findById(id);
        message.setAdminAnswer(adminAnswer);
        messageService.save(message);
        return "redirect:/admin/message";
    }
}
