package com.example.GeekShop.controller.images_product_fields;

import com.example.GeekShop.model.images.ImageTheme;
import com.example.GeekShop.model.product_fields.Theme;
import com.example.GeekShop.repository.images_product_fields.ImageThemeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/theme")
public class ImagesThemeController extends AbstractImageController<Theme, ImageTheme, ImageThemeRepository> {
    private final ImageThemeRepository themeRepository;

    @Autowired
    public ImagesThemeController(ImageThemeRepository themeRepository) {
        this.themeRepository = themeRepository;
    }
    @Override
    public ImageThemeRepository getRepository() {
        return themeRepository;
    }
}