package com.example.GeekShop.controller.admin;

import com.example.GeekShop.model.order.Order;
import com.example.GeekShop.model.order.StatusOfOrder;
import com.example.GeekShop.model.user.User;
import com.example.GeekShop.service.OrderService;
import com.example.GeekShop.service.user.UserService;
import jakarta.validation.constraints.NotNull;
import jakarta.websocket.server.PathParam;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/admin")
public class AdminOrdersController {
    private final OrderService orderService;
    private final UserService userService;

    @Autowired
    public AdminOrdersController(OrderService orderService, UserService userService) {
        this.orderService = orderService;
        this.userService = userService;
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
    @GetMapping("order/{id}")
    public String pageOfSelectedOrder(@NotNull Model model, @PathVariable Long id) {
        model.addAttribute("order", orderService.findById(id));
        return "admin/selected_order";
    }
    @DeleteMapping("/order/{id}")
    public String deleteOrder(@PathVariable Long id) {
        deleteOrderById(id);
        return "redirect:/admin/order";
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
            setFieldsForUserAfterCloseOrder(order);
        }
        return "redirect:/admin/order/{id}";
    }
    public void deleteOrderById(Long id) {
        Order order = orderService.findById(id);
        User user = order.getUser();
        user.getOrders().remove(order);
        userService.saveAfterChange(user);
        orderService.deleteById(id);
    }
    public void setFieldsForUserAfterCloseOrder(Order order) {
        User user = order.getUser();
        user.setSpendMoney(user.getSpendMoney() + order.getPriceOfOrder());
        user.setBonusPoints(order.getPriceOfOrder() / 100);
        user.selectLevelOfSupport();
        userService.saveAfterChange(user);
    }
}
