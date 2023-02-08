package com.example.GeekShop.controller.admin;

import com.example.GeekShop.service.OrderService;
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

@Controller
@RequestMapping("/admin")
public class AdminOrdersController {
    private final OrderService orderService;

    @Autowired
    public AdminOrdersController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/order")
    public String pageOfAllOrders(@NotNull Model model, Principal principal) {
        model.addAttribute("orders", orderService.findAll());
        model.addAttribute("principal", principal);
        return "admin/all_orders";
    }
    @GetMapping("/order/find")
    public String searchByName(@PathParam("username") String username, @NonNull Model model, Principal principal) {
        model.addAttribute("orders", orderService.findByUsername(username));
        model.addAttribute("principal", principal);
        return "admin/all_orders";
    }
    @GetMapping("/order/find-code")
    public String searchByCode(@PathParam("code") Long code, Principal principal, @NonNull Model model) {
        model.addAttribute("orders", orderService.findByCode(code));
        model.addAttribute("principal", principal);
        return "/admin/all_orders";
    }
    @GetMapping("order/{id}")
    public String pageOfSelectedOrder(@NotNull Model model, @PathVariable Long id) {
        model.addAttribute("order", orderService.findById(id));
        return "admin/selected_order";
    }
}
