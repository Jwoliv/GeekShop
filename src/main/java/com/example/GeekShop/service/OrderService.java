package com.example.GeekShop.service;

import com.example.GeekShop.model.order.Order;
import com.example.GeekShop.model.order.StatusOfOrder;
import com.example.GeekShop.model.product.ProductByBasket;
import com.example.GeekShop.model.user.User;
import com.example.GeekShop.repository.OrderRepository;
import com.example.GeekShop.service.user.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.Date;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class OrderService {
    private final UserService userService;
    private final OrderRepository orderRepository;

    public OrderService(UserService userService, OrderRepository orderRepository) {
        this.userService = userService;
        this.orderRepository = orderRepository;
    }

    public List<Order> findAll() {
        return orderRepository.findAll();
    }
    public Order findById(Long id) {
        return orderRepository.findById(id).orElse(null);
    }
    public List<Order> findByUsername(String name) {
        return orderRepository.findByUsername(name);
    }
    public List<Order> findByCode(Long code) {
        return orderRepository.findByCode(String.valueOf(code));
    }
    @Transactional
    public void save(Order order) {
        orderRepository.save(order);
    }
    @Transactional
    public void deleteById(Long id) {
        orderRepository.deleteById(id);
    }
    @Transactional
    public void deleteOrderById(Long id) {
        Order order = findById(id);
        User user = order.getUser();
        user.getOrders().remove(order);
        userService.saveAfterChange(user);
        deleteById(id);
    }
    @Transactional
    public Long generateCodeForOrder() {
        long min = 10000000000000L;
        long max = 99999999999999L;
        max -= min;
        return (long) (Math.random() * ++max) + min;
    }
    @Transactional
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
    @Transactional
    public void createdNewOrderForUser(Principal principal, Order order) {
        if (principal != null && order != null) {
            User user = userService.findByEmail(principal.getName());
            setFieldsForOrder(user, order);
            userService.setFieldsForUserAfterCreateOrder(user, order);
        }
    }
    @Transactional
    public void setCodeOfMailForOrder(Long id, Long code) {
        Order orderById = findById(id);
        orderById.setCodeOfInvoice(code);
        save(orderById);
    }
    @Transactional
    public void changeStatusForOrder(Long id, StatusOfOrder status) {
        Order order = findById(id);
        order.setStatusOfOrder(status);
        save(order);
        if (status == StatusOfOrder.Completed) {
            userService.setFieldsForUserAfterCloseOrder(order);
        }
    }
    @Transactional
    public void setVerifyForOrder(Long id) {
        Order orderById = findById(id);
        orderById.setIsVerify(true);
        save(orderById);
    }
}