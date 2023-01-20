package com.example.GeekShop.repository.product_fields;

import com.example.GeekShop.model.images.ImageProductField;
import com.example.GeekShop.model.product_fields.AbstractProductField;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AbstractProductFieldRepository<E extends AbstractProductField<E, TI>, TI extends ImageProductField<E>> extends JpaRepository<E, Long> { }