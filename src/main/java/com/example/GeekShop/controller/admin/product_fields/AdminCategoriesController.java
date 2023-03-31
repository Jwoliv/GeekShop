package com.example.GeekShop.controller.admin.product_fields;

import com.example.GeekShop.model.images.ImageCategory;
import com.example.GeekShop.model.product_fields.Category;
import com.example.GeekShop.repository.product_fields.CategoryRepository;
import com.example.GeekShop.service.product_fields.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/category")
public class AdminCategoriesController extends AdminAbstractProductsFieldsController<
        ImageCategory, Category, CategoryRepository, CategoryService> {
    @Autowired
    public AdminCategoriesController(CategoryService categoryService) {
        this.setService(categoryService);
        this.setUrl("category");
        this.setTitle("Category");
    }
}
