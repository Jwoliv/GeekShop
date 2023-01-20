package com.example.GeekShop.controller.product;

import com.example.GeekShop.model.images.ImageProduct;
import com.example.GeekShop.repository.product.ImageProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;
import java.util.Objects;

@RestController
@RequestMapping("/product")
public class ImagesProductController {
    private final ImageProductRepository repository;

    @Autowired
    public ImagesProductController(ImageProductRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/images/{id}")
    private ResponseEntity<?> getImageById(@PathVariable Long id) {
        ImageProduct image = repository.findById(id).orElse(null);
        return ResponseEntity.ok()
                .header("fileName", Objects.requireNonNull(image).getOriginalName())
                .contentType(MediaType.valueOf(image.getContentType()))
                .contentLength(image.getSize())
                .body(new InputStreamResource(new ByteArrayInputStream(image.getBytes())));
    }
}
