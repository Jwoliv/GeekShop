package com.example.GeekShop.service.product;

import com.example.GeekShop.model.product.Gender;
import com.example.GeekShop.model.product.Product;
import com.example.GeekShop.model.product.ProductByBasket;
import com.example.GeekShop.model.product_fields.Category;
import com.example.GeekShop.model.product_fields.Season;
import com.example.GeekShop.model.product_fields.Theme;
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
    private final ProductByBasketService productByBasketService;
    @Autowired
    public ProductService(ProductRepository productRepository, UserService userService, ProductByBasketService productByBasketService) {
        this.productRepository = productRepository;
        this.userService = userService;
        this.productByBasketService = productByBasketService;
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
    public List<Product> findProductsByGender(Gender gender) {
        return productRepository.findProductsByGender(gender);
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

    public List<Product> findProductsByCategory(Category category) {
        return productRepository.findProductsByCategory(category);
    }
    public List<Product> findProductsByTheme(Theme theme) {
        return productRepository.findProductsByTheme(theme);
    }
    public List<Product> findProductsBySeason(Season season) {
        return productRepository.findProductsBySeason(season);
    }
    public List<Product> findProductsInTheMainForm(
            Long categoryId, Long themeId, Long seasonId,
            Gender gender, Integer min, Integer max
    ) {
        return productRepository.findProductsInTheMainForm(categoryId, themeId, seasonId, gender, min, max);
    }
    @Transactional
    public void cleanUsersBasketOfProduct(Principal principal) {
        if (principal != null) {
            User user = userService.findByEmail(principal.getName());
            for (ProductByBasket product : user.getBasketOfProducts()) {
                product.getProduct().setNumberProduct(product.getNumberProduct() + product.getProduct().getNumberProduct());
                user.getBasketOfProducts().remove(product);
                save(product.getProduct());
            }
        }
    }
    @Transactional
    public void deleteProductFromBasketById(Principal principal, Long productId) {
        if (principal != null) {
            User user = userService.findByEmail(principal.getName());
            ProductByBasket productByBasket =  productByBasketService.findById(productId);
            Product product = findById(productByBasket.getProduct().getId());
            product.setNumberProduct(product.getNumberProduct() + productByBasket.getNumberProduct());
            user.getBasketOfProducts().remove(productByBasket);
            productByBasketService.deleteById(productId);
            userService.saveAfterChange(user);
        }
    }
}
