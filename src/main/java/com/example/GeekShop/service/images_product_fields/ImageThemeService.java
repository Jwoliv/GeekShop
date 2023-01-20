package com.example.GeekShop.service.images_product_fields;

import com.example.GeekShop.model.images.ImageTheme;
import com.example.GeekShop.model.product_fields.Theme;
import com.example.GeekShop.repository.images_product_fields.ImageThemeRepository;
import org.springframework.stereotype.Service;

@Service
public class ImageThemeService extends AbstractImagesService<Theme, ImageTheme, ImageThemeRepository> {
    public ImageThemeService(ImageThemeRepository repository) {
        super(repository);
    }
}
