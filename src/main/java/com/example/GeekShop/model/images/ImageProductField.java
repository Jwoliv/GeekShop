package com.example.GeekShop.model.images;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@MappedSuperclass
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class ImageProductField<T> extends AbstractImage {
    @ManyToOne
    @JoinColumn
    protected T element;
}
