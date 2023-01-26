package com.example.GeekShop.controller.product_fields;

import com.example.GeekShop.model.images.ImageCategory;
import com.example.GeekShop.model.product_fields.Category;
import com.example.GeekShop.repository.images_product_fields.ImageCategoryRepository;
import com.example.GeekShop.repository.product_fields.CategoryRepository;
import com.example.GeekShop.service.images_product_fields.ImageCategoryService;
import com.example.GeekShop.service.product_fields.CategoryService;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Getter
@Setter
@RequestMapping("/category")
public class CategoriesController extends AbstractProductFieldsController<
        Category,
        ImageCategory,
        ImageCategoryRepository,
        ImageCategoryService,
        CategoryRepository,
        CategoryService
        > {
    private final CategoryService categoryService;
    private final ImageCategoryService imageCategoryService;
    @Autowired
    public CategoriesController(CategoryService categoryService, ImageCategoryService imageCategoryService) {
        this.categoryService = categoryService;
        this.imageCategoryService = imageCategoryService;
        this.setUrl("category");
        this.setNameSingle("Category");
        this.setNamePlural("Categories");
        this.setNewElement(new Category());
    }

    @Override
    public CategoryService getService() {
        return categoryService;
    }
    @Override
    public ImageCategory getImage() {
        return new ImageCategory();
    }
    @Override
    public ImageCategoryService getImageService() {
        return getImageCategoryService();
    }
}
