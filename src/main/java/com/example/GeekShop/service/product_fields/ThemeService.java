package com.example.GeekShop.service.product_fields;

import com.example.GeekShop.model.images.ImageTheme;
import com.example.GeekShop.model.product_fields.Theme;
import com.example.GeekShop.repository.product_fields.ThemeRepository;
import org.springframework.stereotype.Service;

@Service
public class ThemeService extends AbstractProductFieldService<Theme, ImageTheme, ThemeRepository> {
    protected ThemeService(ThemeRepository repository) {
        super(repository);
    }
}
