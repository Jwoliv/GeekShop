package com.example.GeekShop.controller;

import com.example.GeekShop.model.order.Order;
import com.example.GeekShop.model.order.StatusOfOrder;
import com.example.GeekShop.model.product.ProductByBasket;
import com.example.GeekShop.model.user.User;
import com.example.GeekShop.service.OrderService;
import com.example.GeekShop.service.user.UserService;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;

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
        return "/order/new_order";
    }

    @PostMapping
    public String formNewOrder(Principal principal, Order order) {
        User user = userService.findByEmail(principal.getName());
        setFieldsForOrder(user, order);
        setFieldsForUserAfterCreateOrder(user, order);
        return "redirect:/profile";
    }
    @DeleteMapping("/{id}")
    public String deleteOrder(@PathVariable Long id) {
        deleteOrderById(id);
        return "redirect:/admin/order";
    }
    @PatchMapping("/{id}/change_status")
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
    @PatchMapping("/{id}/set_code")
    public String setCodeForOrder(@PathVariable Long id, @RequestParam("code_of_mail") Long code) {
        Order orderById = orderService.findById(id);
        orderById.setCodeOfInvoice(code);
        orderService.save(orderById);
        return "redirect:/admin/order/{id}";
    }
    @PatchMapping("/{id}/is_verify")
    public String setVerifyStatus(@PathVariable Long id) {
        Order orderById = orderService.findById(id);
        orderById.setIsVerify(true);
        orderService.save(orderById);
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
    public void setFieldsForOrder(User user, Order order) {
        order.setProductsOfOrders(user.getBasketOfProducts());
        order.setStatusOfOrder(StatusOfOrder.Processed);
        order.setDateOfCreate(new Date());
        order.setUser(user);
        order.setPriceOfOrder(user.getTotalBillOfBasket());
        order.setCodeOfOrder(generateCodeForOrder());
        order.setIsVerify(false);
        for (ProductByBasket productByBasket: order.getProductsOfOrders()) {
            productByBasket.getOrders().add(order);
        }
    }
    public void setFieldsForUserAfterCreateOrder(User user, Order order) {
        user.getOrders().add(order);
        user.setBasketOfProducts(new ArrayList<>());
        user.setBonusPoints(0);
        userService.saveAfterChange(user);
    }
    public Long generateCodeForOrder() {
        long min = 10000000000000L;
        long max = 99999999999999L;
        max -= min;
        return (long) (Math.random() * ++max) + min;
    }
}
