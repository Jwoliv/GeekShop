package com.example.GeekShop.model;

import com.example.GeekShop.model.images.ImageProduct;
import com.example.GeekShop.model.product_fields.Category;
import com.example.GeekShop.model.product_fields.Season;
import com.example.GeekShop.model.product_fields.Theme;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@ToString
@Component
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty
    private String name;
    @NotNull
    @Min(value = 0)
    private Integer price;
    @NotNull
    @Min(value = 0)
    @Max(value = 99)
    private Integer discount;
    @NotNull
    @Min(value = 0)
    private Integer numberProduct;
    private Float rating = 0.0F;
    private Long previewsId;
    @ManyToOne
    private Theme theme;
    @ManyToOne
    private Category category;
    @ManyToOne
    private Season season;
    @OneToMany(mappedBy = "element", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<ImageProduct> images = new ArrayList<>();

    public void addImages(ImageProduct image, Product element) {
        image.setElement(element);
        images.add(image);
    }
}
