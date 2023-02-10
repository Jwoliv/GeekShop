package com.example.GeekShop.repository.product;

import com.example.GeekShop.model.product.Gender;
import com.example.GeekShop.model.product.Product;
import com.example.GeekShop.model.product_fields.Category;
import com.example.GeekShop.model.product_fields.Season;
import com.example.GeekShop.model.product_fields.Theme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("SELECT P FROM Product AS P WHERE UPPER(P.name) LIKE %:name%")
    List<Product> findProductsByName(@Param("name") String name);
    List<Product> findProductsByGender(Gender gender);
    List<Product> findProductsByCategory(Category category);
    List<Product> findProductsByTheme(Theme theme);
    List<Product> findProductsBySeason(Season season);
}
