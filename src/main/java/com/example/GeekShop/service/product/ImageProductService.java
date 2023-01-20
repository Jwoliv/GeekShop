package com.example.GeekShop.service.product;

import com.example.GeekShop.model.Product;
import com.example.GeekShop.model.images.ImageProduct;
import com.example.GeekShop.repository.product.ImageProductRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Data
public class ImageProductService {
    private ImageProductRepository repository;
    @Autowired
    public ImageProductService(ImageProductRepository repository) {
        this.repository = repository;
    }

    public ImageProduct findById(Long id) {
        return repository.findById(id).orElse(null);
    }
    @Transactional
    public void deleteAllByElement(Product element) {
        repository.deleteAllByElement(element);
    }
}
