package com.example.GeekShop.controller;

import com.example.GeekShop.model.order.Order;
import com.example.GeekShop.model.user.User;
import com.example.GeekShop.service.OrderService;
import com.example.GeekShop.service.user.UserService;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/order")
public class OrdersController {
    private final UserService userService;
    private final Order order;
    private final OrderService orderService;

    @Autowired
    public OrdersController(UserService userService, Order order, OrderService orderService) {
        this.userService = userService;
        this.order = order;
        this.orderService = orderService;
    }
    @GetMapping
    public String pageOfOrders(@NonNull Model model, Principal principal) {
        User user = userService.findByEmail(principal.getName());
        model.addAttribute("all_order", user.getOrders());
        return "/order/all_order";
    }
    @GetMapping("/{id}")
    public String pageOfSelectedProduct(@PathVariable Long id, @NonNull Model model, Principal principal) {
        User user = userService.findByEmail(principal.getName());
        Order order = orderService.findById(id);
        if (user != null && user.getOrders().contains(order)) {
            model.addAttribute("model", order);
            model.addAttribute("principal", principal);
            model.addAttribute("user", user);
        }
        return "/order/selected_order";
    }
    @GetMapping("/new")
    public String mainPageOfOrders() {
        return "/order/new_order";
    }

    @PostMapping
    public String formNewOrder(Principal principal) {
        User user = userService.findByEmail(principal.getName());
        orderService.setFieldsForOrder(user, order);
        orderService.setFieldsForUserAfterCreateOrder(user, order);
        return "redirect:/profile";
    }
}
