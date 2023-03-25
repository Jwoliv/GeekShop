package com.example.GeekShop.service.images_product_fields;

import com.example.GeekShop.model.images.ImageProductField;
import com.example.GeekShop.model.product_fields.AbstractProductField;
import com.example.GeekShop.repository.images_product_fields.ImageRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@Data
public abstract class AbstractImagesService<
        E extends AbstractProductField<E, TI>,
        TI extends ImageProductField<E>,
        R extends ImageRepository<E, TI>> {
    public final R repository;

    @Autowired
    public AbstractImagesService(R repository) {
        this.repository = repository;
    }

    public TI findById(Long id) {
        return repository.findById(id).orElse(null);
    }
    @Transactional
    public void deleteAllByElement(E element) {
        repository.deleteAllByElement(element);
    }

    public void setFieldsForImage(TI image, MultipartFile file) throws IOException {
        if (file != null) {
            image.setName(file.getName());
            image.setOriginalName(file.getOriginalFilename());
            image.setContentType(file.getContentType());
            image.setSize(file.getSize());
            image.setBytes(file.getBytes());
        }
    }
}
