package com.example.GeekShop.repository.images_product_fields;

import com.example.GeekShop.model.images.ImageSeason;
import com.example.GeekShop.model.product_fields.Season;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageSeasonRepository extends ImageRepository<Season, ImageSeason> { }
