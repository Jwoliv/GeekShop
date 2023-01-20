package com.example.GeekShop.service.images_product_fields;

import com.example.GeekShop.model.images.ImageCategory;
import com.example.GeekShop.model.product_fields.Category;
import com.example.GeekShop.repository.images_product_fields.ImageCategoryRepository;
import org.springframework.stereotype.Service;

@Service
public class ImageCategoryService extends AbstractImagesService<Category, ImageCategory, ImageCategoryRepository> {
    public ImageCategoryService(ImageCategoryRepository repository) {
        super(repository);
    }
}
