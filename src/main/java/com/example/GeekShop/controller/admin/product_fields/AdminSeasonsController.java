package com.example.GeekShop.controller.admin.product_fields;

import com.example.GeekShop.model.images.ImageSeason;
import com.example.GeekShop.model.product_fields.Season;
import com.example.GeekShop.repository.product_fields.SeasonRepository;
import com.example.GeekShop.service.product_fields.SeasonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/season")
public class AdminSeasonsController extends AdminAbstractProductsFieldsController<
        ImageSeason, Season, SeasonRepository, SeasonService> {
    @Autowired
    public AdminSeasonsController(SeasonService seasonService) {
        this.setService(seasonService);
        this.setUrl("season");
        this.setTitle("Seasons");
    }
}
