package com.example.GeekShop.model.product_fields;

import com.example.GeekShop.model.images.ImageTheme;
import jakarta.persistence.Entity;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Entity
@Component
public class Theme extends AbstractProductField<Theme, ImageTheme> implements Serializable { }
