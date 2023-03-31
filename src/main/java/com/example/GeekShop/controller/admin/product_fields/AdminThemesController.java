package com.example.GeekShop.controller.admin.product_fields;

import com.example.GeekShop.model.images.ImageTheme;
import com.example.GeekShop.model.product_fields.Theme;
import com.example.GeekShop.repository.product_fields.ThemeRepository;
import com.example.GeekShop.service.product_fields.ThemeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/theme")
public class AdminThemesController extends AdminAbstractProductsFieldsController<
        ImageTheme, Theme, ThemeRepository, ThemeService> {
    @Autowired
    public AdminThemesController(ThemeService themeService) {
        this.setService(themeService);
        this.setUrl("theme");
        this.setTitle("Themes");
    }
}
