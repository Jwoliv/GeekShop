package com.example.GeekShop.repository.product;

import com.example.GeekShop.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("SELECT P FROM Product AS P " +
           "WHERE P.category.id = :categoryId " +
                "AND P.theme.id = :themeId " +
                "AND P.season.id = :seasonId " +
            "ORDER BY P.id DESC LIMIT 3"
    )
    List<Product> findRecommendedProducts(
            @Param("categoryId") Long categoryId,
            @Param("themeId") Long themeId,
            @Param("seasonId") Long seasonId
    );
}
