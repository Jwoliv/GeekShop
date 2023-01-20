package com.example.GeekShop.controller.images_product_fields;

import com.example.GeekShop.model.images.ImageSeason;
import com.example.GeekShop.model.product_fields.Season;
import com.example.GeekShop.repository.images_product_fields.ImageSeasonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/season")
public class ImagesSeasonController extends AbstractImageController<Season, ImageSeason, ImageSeasonRepository> {
    private final ImageSeasonRepository seasonRepository;

    @Autowired
    public ImagesSeasonController(ImageSeasonRepository seasonRepository) {
        this.seasonRepository = seasonRepository;
    }
    @Override
    public ImageSeasonRepository getRepository() {
        return seasonRepository;
    }
}