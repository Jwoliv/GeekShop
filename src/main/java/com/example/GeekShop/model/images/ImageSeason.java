package com.example.GeekShop.model.images;

import com.example.GeekShop.model.product_fields.Season;
import jakarta.persistence.Entity;
import org.springframework.stereotype.Component;

@Component
@Entity
public class ImageSeason extends ImageProductField<Season> {
}
