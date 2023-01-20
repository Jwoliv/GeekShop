package com.example.GeekShop.model.product_fields;

import com.example.GeekShop.model.images.ImageCategory;
import jakarta.persistence.Entity;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Entity
@Component
public class Category extends AbstractProductField<Category, ImageCategory> implements Serializable { }
