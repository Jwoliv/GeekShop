package com.example.GeekShop.service;

import com.example.GeekShop.model.order.Order;
import com.example.GeekShop.model.order.StatusOfOrder;
import com.example.GeekShop.model.product.ProductByBasket;
import com.example.GeekShop.model.user.User;
import com.example.GeekShop.repository.OrderRepository;
import com.example.GeekShop.service.user.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class OrderService {
    private final OrderRepository orderRepository;
    private final UserService userService;

    public OrderService(OrderRepository orderRepository, UserService userService) {
        this.orderRepository = orderRepository;
        this.userService = userService;
    }

    public List<Order> findAll() {
        return orderRepository.findAll();
    }
    public Order findById(Long id) {
        return orderRepository.findById(id).orElse(null);
    }
    @Transactional
    public void save(Order order) {
        orderRepository.save(order);
    }
    @Transactional
    public void deleteById(Long id) {
        orderRepository.deleteById(id);
    }
    public void setFieldsForOrder(User user, Order order) {
        order.setProductsOfOrders(user.getBasketOfProducts());
        order.setStatusOfOrder(StatusOfOrder.Processed);
        order.setDateOfCreate(new Date());
        order.setUser(user);
        order.setPriceOfOrder(user.getTotalBillOfBasket());
        for (ProductByBasket productByBasket: order.getProductsOfOrders()) {
            productByBasket.getOrders().add(order);
        }
        save(order);
    }
    public void setFieldsForUserAfterCreateOrder(User user, Order order) {
        user.getOrders().add(order);
        user.setBasketOfProducts(new ArrayList<>());
        user.setBonusPoints(0);
        userService.saveAfterChange(user);
    }
}