package com.example.GeekShop.repository.product;

import com.example.GeekShop.model.product.ProductByBasket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductByBasketRepository extends JpaRepository<ProductByBasket, Long> {
}
