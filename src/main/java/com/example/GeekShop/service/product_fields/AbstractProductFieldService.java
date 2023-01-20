package com.example.GeekShop.service.product_fields;

import com.example.GeekShop.model.images.ImageProductField;
import com.example.GeekShop.model.product_fields.AbstractProductField;
import com.example.GeekShop.repository.product_fields.AbstractProductFieldRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public abstract class AbstractProductFieldService<
        E extends AbstractProductField<E, TI>,
        TI extends ImageProductField<E>,
        R extends AbstractProductFieldRepository<E, TI>
        > {
    private final R repository;
    protected AbstractProductFieldService(R repository) {
        this.repository = repository;
    }

    public List<E> findAll() {
        return repository.findAll();
    }
    public E findById(Long id) {
        return repository.findById(id).orElse(null);
    }
    @Transactional
    public void save(E entity) {
        repository.save(entity);
    }
    @Transactional
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
