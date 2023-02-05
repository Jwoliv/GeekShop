package com.example.GeekShop.repository;

import com.example.GeekShop.model.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("SELECT O FROM Order AS O WHERE O.user.firstname LIKE %:name% OR O.user.lastname LIKE %:name%")
    List<Order> findByUsername(String name);
}
