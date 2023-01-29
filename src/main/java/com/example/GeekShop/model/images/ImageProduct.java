package com.example.GeekShop.model.images;

import com.example.GeekShop.model.product.Product;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.stereotype.Component;

@Data
@Entity
@ToString
@Component
@NoArgsConstructor
@AllArgsConstructor
public class ImageProduct extends AbstractImage {
    @ManyToOne
    @JoinColumn
    protected Product element;
}
