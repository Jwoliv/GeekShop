package com.example.GeekShop.controller.images_product_fields;


import com.example.GeekShop.model.images.ImageProductField;
import com.example.GeekShop.model.product_fields.AbstractProductField;
import com.example.GeekShop.repository.images_product_fields.ImageRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.ByteArrayInputStream;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AbstractImageController<
        E extends AbstractProductField<E, TI>,
        TI extends ImageProductField<E>,
        R extends ImageRepository<E, TI>
        >{
    private R repository;
    @GetMapping("/images/{id}")
    private ResponseEntity<?> getImageById(@PathVariable Long id) {
        TI image = getRepository().findById(id).orElse(null);
        return ResponseEntity.ok()
                .header("fileName", Objects.requireNonNull(image).getOriginalName())
                .contentType(MediaType.valueOf(image.getContentType()))
                .contentLength(image.getSize())
                .body(new InputStreamResource(new ByteArrayInputStream(image.getBytes())));
    }
}

