package com.example.GeekShop.service.images_product_fields;

import com.example.GeekShop.model.images.ImageSeason;
import com.example.GeekShop.model.product_fields.Season;
import com.example.GeekShop.repository.images_product_fields.ImageSeasonRepository;
import org.springframework.stereotype.Service;

@Service
public class ImageSeasonService extends AbstractImagesService<Season, ImageSeason, ImageSeasonRepository> {
    public ImageSeasonService(ImageSeasonRepository repository) {
        super(repository);
    }
}
