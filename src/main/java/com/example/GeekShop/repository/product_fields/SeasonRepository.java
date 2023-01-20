package com.example.GeekShop.repository.product_fields;

import com.example.GeekShop.model.images.ImageSeason;
import com.example.GeekShop.model.product_fields.Season;
import org.springframework.stereotype.Repository;

@Repository
public interface SeasonRepository extends AbstractProductFieldRepository<Season, ImageSeason> { }
