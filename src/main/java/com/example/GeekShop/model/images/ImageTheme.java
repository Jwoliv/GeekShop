package com.example.GeekShop.model.images;

import com.example.GeekShop.model.product_fields.Theme;
import jakarta.persistence.Entity;
import org.springframework.stereotype.Component;

@Component
@Entity
public class ImageTheme extends ImageProductField<Theme> {
}
