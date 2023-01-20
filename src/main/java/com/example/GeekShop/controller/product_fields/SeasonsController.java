package com.example.GeekShop.controller.product_fields;

import com.example.GeekShop.model.images.ImageSeason;
import com.example.GeekShop.model.product_fields.Season;
import com.example.GeekShop.repository.images_product_fields.ImageSeasonRepository;
import com.example.GeekShop.repository.product_fields.SeasonRepository;
import com.example.GeekShop.service.images_product_fields.ImageSeasonService;
import com.example.GeekShop.service.product_fields.SeasonService;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Getter
@Setter
@RequestMapping("/season")
public class SeasonsController extends AbstractProductFieldsController<
        Season,
        ImageSeason,
        ImageSeasonRepository,
        ImageSeasonService,
        SeasonRepository,
        SeasonService
        > {
    private final SeasonService seasonService;
    private final ImageSeasonService imageSeasonService;
    @Autowired
    public SeasonsController(SeasonService seasonService, ImageSeasonService imageSeasonService) {
        this.seasonService = seasonService;
        this.imageSeasonService = imageSeasonService;
        this.setUrl("season");
        this.setNameSingle("season");
        this.setNamePlural("seasons");
        this.setNewElement(new Season());
    }

    @Override
    public SeasonService getService() {
        return seasonService;
    }
    @Override
    public ImageSeason getImage() {
        return new ImageSeason();
    }
    @Override
    public ImageSeasonService getImageService() {
        return getImageSeasonService();
    }
}
