package com.example.GeekShop.service.product;

import com.example.GeekShop.model.Product;
import com.example.GeekShop.repository.product.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ProductService {
    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }
    public List<Product> findRecommendedProduct(Long cId, Long tId, Long sId) {
        return productRepository.findRecommendedProducts(cId, tId, sId);
    }
    public Product findById(Long id) {
        return productRepository.findById(id).orElse(null);
    }
    @Transactional
    public void save(Product product) {
        productRepository.save(product);
    }
    @Transactional
    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }
}
