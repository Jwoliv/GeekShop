package com.example.GeekShop.controller.images_product_fields;

import com.example.GeekShop.model.images.ImageCategory;
import com.example.GeekShop.model.product_fields.Category;
import com.example.GeekShop.repository.images_product_fields.ImageCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/category")
public class ImagesCategoryController extends AbstractImageController<Category, ImageCategory, ImageCategoryRepository> {
    private final ImageCategoryRepository categoryRepository;

    @Autowired
    public ImagesCategoryController(ImageCategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public ImageCategoryRepository getRepository() {
        return categoryRepository;
    }
}
