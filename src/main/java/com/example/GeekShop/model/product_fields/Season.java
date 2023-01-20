package com.example.GeekShop.model.product_fields;

import com.example.GeekShop.model.images.ImageSeason;
import jakarta.persistence.Entity;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Entity
@Component
public class Season extends AbstractProductField<Season, ImageSeason> implements Serializable { }
