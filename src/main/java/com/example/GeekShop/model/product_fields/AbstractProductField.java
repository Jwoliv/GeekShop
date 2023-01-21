package com.example.GeekShop.model.product_fields;

import com.example.GeekShop.model.Product;
import com.example.GeekShop.model.images.ImageProductField;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@ToString
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class AbstractProductField<E extends AbstractProductField<E, TI>, TI extends ImageProductField<E>> implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
    @NotEmpty(message = "The name of this element cannot be empty")
    protected String name;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @ToString.Exclude
    protected List<TI> images = new ArrayList<>();
    protected Long previewsId;
    @OneToMany(fetch = FetchType.LAZY)
    @ToString.Exclude
    protected List<Product> products = new ArrayList<>();
    public void addImages(TI image, E element) {
        image.setElement(element);
        images.add(image);
    }
}
