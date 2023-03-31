package com.example.GeekShop.controller.product_fields;

import com.example.GeekShop.model.images.ImageSeason;
import com.example.GeekShop.model.product.Product;
import com.example.GeekShop.model.product_fields.Season;
import com.example.GeekShop.repository.images_product_fields.ImageSeasonRepository;
import com.example.GeekShop.repository.product_fields.SeasonRepository;
import com.example.GeekShop.service.images_product_fields.ImageSeasonService;
import com.example.GeekShop.service.product.ProductService;
import com.example.GeekShop.service.product_fields.SeasonService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

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
    private final ProductService productService;
    @Autowired
    public SeasonsController(SeasonService seasonService, ImageSeasonService imageSeasonService, ProductService productService) {
        this.seasonService = seasonService;
        this.imageSeasonService = imageSeasonService;
        this.productService = productService;
        this.setUrl("season");
        this.setNameSingle("Season");
        this.setNamePlural("Seasons");
        this.setNewElement(new Season());
    }

    @Override
    protected List<Product> productsByProductFields(Season season) {
        return productService.findProductsBySeason(season);
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
