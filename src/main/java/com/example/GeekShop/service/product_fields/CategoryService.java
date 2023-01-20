package com.example.GeekShop.service.product_fields;

import com.example.GeekShop.model.images.ImageCategory;
import com.example.GeekShop.model.product_fields.Category;
import com.example.GeekShop.repository.product_fields.CategoryRepository;
import org.springframework.stereotype.Service;

@Service
public class CategoryService extends AbstractProductFieldService<Category, ImageCategory, CategoryRepository> {
    protected CategoryService(CategoryRepository repository) {
        super(repository);
    }
}
