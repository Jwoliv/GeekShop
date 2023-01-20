package com.example.GeekShop.repository.product;

import com.example.GeekShop.model.Product;
import com.example.GeekShop.model.images.ImageProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageProductRepository extends JpaRepository<ImageProduct, Long> {
    void deleteAllByElement(Product product);
}
