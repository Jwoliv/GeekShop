package com.example.GeekShop.controller;

import com.example.GeekShop.model.order.Order;
import com.example.GeekShop.model.order.StatusOfOrder;
import com.example.GeekShop.model.user.User;
import com.example.GeekShop.service.OrderService;
import com.example.GeekShop.service.user.UserService;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/order")
public class OrdersController {
    private final UserService userService;
    private final OrderService orderService;

    @Autowired
    public OrdersController(UserService userService, OrderService orderService) {
        this.userService = userService;
        this.orderService = orderService;
    }
    @GetMapping("/{id}")
    public String pageOfSelectedProduct(@PathVariable Long id, @NonNull Model model, Principal principal) {
        User user = userService.findByEmail(principal.getName());
        Order order = orderService.findById(id);
        if (user != null && user.getOrders().contains(order)) {
            model.addAttribute("model", order);
            model.addAttribute("principal", principal);
            model.addAttribute("user", user);
            model.addAttribute("order", orderService.findById(id));
        }
        return "/order/selected_order";
    }
    @GetMapping("/new")
    public String mainPageOfOrders(@NonNull Model model, Principal principal) {
        User user = userService.findByEmail(principal.getName());
        model.addAttribute("order", new Order());
        model.addAttribute("user", user);
        model.addAttribute("principal", principal);
        return "/order/new_order";
    }

    @PostMapping
    public String formNewOrder(Principal principal, Order order) {
        orderService.createdNewOrderForUser(principal, order);
        return "redirect:/profile";
    }
    @DeleteMapping("/{id}")
    public String deleteOrder(@PathVariable Long id) {
        orderService.deleteOrderById(id);
        return "redirect:/admin/order";
    }
    @PatchMapping("/{id}/change_status")
    public String changeStatusOfOrder(@PathVariable Long id, @RequestParam StatusOfOrder status) {
        orderService.changeStatusForOrder(id, status);
        return "redirect:/admin/order/{id}";
    }
    @PatchMapping("/{id}/set_code")
    public String setCodeForOrder(@PathVariable Long id, @RequestParam("code_of_mail") Long code) {
        orderService.setCodeOfMailForOrder(id, code);
        return "redirect:/admin/order/{id}";
    }
    @PatchMapping("/{id}/is_verify")
    public String setVerifyStatus(@PathVariable Long id) {
        orderService.setVerifyForOrder(id);
        return "redirect:/admin/order/{id}";
    }
}
