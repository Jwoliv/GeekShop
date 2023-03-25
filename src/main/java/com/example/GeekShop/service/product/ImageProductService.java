package com.example.GeekShop.service.product;

import com.example.GeekShop.model.product.Product;
import com.example.GeekShop.model.images.ImageProduct;
import com.example.GeekShop.repository.product.ImageProductRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Data
public class ImageProductService {
    private ImageProductRepository repository;
    private ProductService productService;
    @Autowired
    public ImageProductService(ImageProductRepository repository) {
        this.repository = repository;
    }
    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }
    public ImageProduct findById(Long id) {
        return repository.findById(id).orElse(null);
    }
    @Transactional
    public void deleteAllByElement(Product element) {
        repository.deleteAllByElement(element);
    }
    public ImageProduct toImageEntity(MultipartFile file) throws IOException {
        ImageProduct image = new ImageProduct();
        image.setName(file.getName());
        image.setOriginalName(file.getOriginalFilename());
        image.setContentType(file.getContentType());
        image.setSize(file.getSize());
        image.setBytes(file.getBytes());
        return image;
    }
    private void addImageForElement(Product element, MultipartFile file) {
        if (element != null && file != null) {
            try {
                element.addImages(toImageEntity(file), element);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void saveImages(
            List<MultipartFile> files, Product element
    ) {
        element.setPreviewsId(null);
        element.setImages(new ArrayList<>());
        deleteAllByElement(element);
        try {
            ImageProduct image1 = toImageEntity(files.get(0));
            image1.setIsPreviews(true);
            element.addImages(image1, element);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        for (int i = 1; i < 5; i++) {
            addImageForElement(element, files.get(i));
        }
        productService.save(element);
        element.setPreviewsId(element.getImages().get(0).getId());
        productService.save(element);
    }
}
