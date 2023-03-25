package com.example.GeekShop.service.product;

import com.example.GeekShop.model.product.ProductByBasket;
import com.example.GeekShop.repository.product.ProductByBasketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ProductByBasketService {
    private final ProductByBasketRepository productRepository;

    @Autowired
    public ProductByBasketService(ProductByBasketRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductByBasket> findAll() {
        return productRepository.findAll();
    }
    public ProductByBasket findById(Long id) {
        return productRepository.findById(id).orElse(null);
    }
    @Transactional
    public void save(ProductByBasket product) {
        productRepository.save(product);
    }
    @Transactional
    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }
}