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

@Service
@Data
public class ImageProductService {
    private ImageProductRepository repository;
    private final ProductService productService;
    @Autowired
    public ImageProductService(ImageProductRepository repository, ProductService productService) {
        this.repository = repository;
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
            MultipartFile file1, MultipartFile file2, MultipartFile file3, MultipartFile file4, MultipartFile file5, Product element
    ) {
        element.setPreviewsId(null);
        element.setImages(new ArrayList<>());
        deleteAllByElement(element);
        try {
            ImageProduct image1 = toImageEntity(file1);
            image1.setIsPreviews(true);
            element.addImages(image1, element);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        addImageForElement(element, file1);
        addImageForElement(element, file2);
        addImageForElement(element, file3);
        addImageForElement(element, file4);
        addImageForElement(element, file5);
        productService.save(element);
        element.setPreviewsId(element.getImages().get(0).getId());
        productService.save(element);
    }
}
