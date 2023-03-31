package com.example.GeekShop.controller.product_fields;

import com.example.GeekShop.model.images.ImageTheme;
import com.example.GeekShop.model.product.Product;
import com.example.GeekShop.model.product_fields.Theme;
import com.example.GeekShop.repository.images_product_fields.ImageThemeRepository;
import com.example.GeekShop.repository.product_fields.ThemeRepository;
import com.example.GeekShop.service.images_product_fields.ImageThemeService;
import com.example.GeekShop.service.product.ProductService;
import com.example.GeekShop.service.product_fields.ThemeService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@Getter
@Setter
@RequestMapping("/theme")
public class ThemesController extends AbstractProductFieldsController<
        Theme,
        ImageTheme,
        ImageThemeRepository,
        ImageThemeService,
        ThemeRepository,
        ThemeService
        > {
    private final ThemeService themeService;
    private final ImageThemeService imageThemeService;
    private final ProductService productService;

    @Autowired
    public ThemesController(ThemeService themeService, ImageThemeService imageThemeService, ProductService productService) {
        this.themeService = themeService;
        this.imageThemeService = imageThemeService;
        this.productService = productService;
        this.setUrl("theme");
        this.setNameSingle("Theme");
        this.setNamePlural("Themes");
        this.setNewElement(new Theme());
    }

    @Override
    protected List<Product> productsByProductFields(Theme theme) {
        return productService.findProductsByTheme(theme);
    }
    @Override
    public ThemeService getService() {
        return themeService;
    }
    @Override
    public ImageTheme getImage() {
        return new ImageTheme();
    }

    @Override
    public ImageThemeService getImageService() {
        return getImageThemeService();
    }
}
