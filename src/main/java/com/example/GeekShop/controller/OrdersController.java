package com.example.GeekShop.controller;

import com.example.GeekShop.model.order.Order;
import com.example.GeekShop.model.order.StatusOfOrder;
import com.example.GeekShop.model.product.ProductByBasket;
import com.example.GeekShop.model.user.User;
import com.example.GeekShop.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;

@Controller
@RequestMapping("/order")
public class OrdersController {
    private final UserService userService;
    private final Order order;

    @Autowired
    public OrdersController(UserService userService, Order order) {
        this.userService = userService;
        this.order = order;
    }
    @GetMapping
    public String pageOfOrders() {
        return "/order/orders";
    }
    @GetMapping("/new")
    public String mainPageOfOrders() {
        return "/order/new_order";
    }

    @PostMapping
    public String formNewOrder(Principal principal) {
        User user = userService.findByEmail(principal.getName());
        setFieldsForOrder(user);
        setFieldsForUserAfterCreateOrder(user);
        return "redirect:/profile";
    }

    private void setFieldsForOrder(User user) {
        order.setProductsOfOrders(user.getBasketOfProducts());
        order.setStatusOfOrder(StatusOfOrder.Processed);
        order.setDateOfCreate(new Date());
        order.setUser(user);
        order.setPriceOfOrder(user.getTotalBillOfBasket());
        for (ProductByBasket productByBasket: order.getProductsOfOrders()) {
            productByBasket.getOrders().add(order);
        }
    }
    private void setFieldsForUserAfterCreateOrder(User user) {
        user.getOrders().add(order);
        user.setBasketOfProducts(new ArrayList<>());
        userService.saveAfterChange(user);
    }
}
