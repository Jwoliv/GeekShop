package com.example.GeekShop.repository.product_fields;

import com.example.GeekShop.model.images.ImageCategory;
import com.example.GeekShop.model.product_fields.Category;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends AbstractProductFieldRepository<Category, ImageCategory> { }
