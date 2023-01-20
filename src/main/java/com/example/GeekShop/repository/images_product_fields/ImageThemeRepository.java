package com.example.GeekShop.repository.images_product_fields;

import com.example.GeekShop.model.images.ImageTheme;
import com.example.GeekShop.model.product_fields.Theme;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageThemeRepository extends ImageRepository<Theme, ImageTheme> {
}
