package com.example.GeekShop.service;

import com.example.GeekShop.model.order.Order;
import com.example.GeekShop.model.product.Product;
import com.example.GeekShop.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class OrderService {
    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
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
}