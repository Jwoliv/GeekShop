package com.example.GeekShop.repository.images_product_fields;

import com.example.GeekShop.model.images.ImageProductField;
import com.example.GeekShop.model.product_fields.AbstractProductField;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface ImageRepository<E extends AbstractProductField<E, TI>, TI extends ImageProductField<E>> extends JpaRepository<TI, Long> {
    void deleteAllByElement(E element);
}
