package com.example.GeekShop.repository.images_product_fields;

import com.example.GeekShop.model.images.ImageCategory;
import com.example.GeekShop.model.product_fields.Category;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageCategoryRepository extends ImageRepository<Category , ImageCategory> { }
