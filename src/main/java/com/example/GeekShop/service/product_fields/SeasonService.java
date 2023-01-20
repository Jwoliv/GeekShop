package com.example.GeekShop.service.product_fields;

import com.example.GeekShop.model.images.ImageSeason;
import com.example.GeekShop.model.product_fields.Season;
import com.example.GeekShop.repository.product_fields.SeasonRepository;
import org.springframework.stereotype.Service;

@Service
public class SeasonService extends AbstractProductFieldService<Season, ImageSeason, SeasonRepository> {
    protected SeasonService(SeasonRepository repository) {
        super(repository);
    }
}
