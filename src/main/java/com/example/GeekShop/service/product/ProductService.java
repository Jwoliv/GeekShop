package com.example.GeekShop.service.product;

import com.example.GeekShop.model.product.Product;
import com.example.GeekShop.model.user.User;
import com.example.GeekShop.repository.product.ProductRepository;
import com.example.GeekShop.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@Transactional(readOnly = true)
public class ProductService {
    private final ProductRepository productRepository;
    private final UserService userService;
    @Autowired
    public ProductService(ProductRepository productRepository, UserService userService) {
        this.productRepository = productRepository;
        this.userService = userService;
    }
    public List<Product> findProductsByName(String name) {
        return productRepository.findProductsByName(name);
    }
    public List<Product> findAll() {
        return productRepository.findAll();
    }
    public List<Product> findRecommendedProduct(Principal principal) {
        User user = userService.findByEmail(principal.getName());
        List<Product> products = new ArrayList<>();
        Random random = new Random();
        if (user.getViewedProducts().size() >= 3) {
            while (products.size() != 3) {
                int index = random.nextInt(0, user.getViewedProducts().size());
                if (products.contains(user.getViewedProducts().get(index))) {
                    continue;
                }
                products.add(user.getViewedProducts().get(index));
            }
        }
        return products;
    }
    public Product findById(Long id) {
        return productRepository.findById(id).orElse(null);
    }
    @Transactional
    public void save(Product product) {
        product.calculateRating();
        productRepository.save(product);
    }
    @Transactional
    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }
}
