package com.example.GeekShop.model;

import com.example.GeekShop.model.images.ImageProduct;
import com.example.GeekShop.model.product_fields.Category;
import com.example.GeekShop.model.product_fields.Season;
import com.example.GeekShop.model.product_fields.Theme;
import com.example.GeekShop.model.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
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
    @OneToMany(mappedBy = "element", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<ImageProduct> images = new ArrayList<>();
    @ManyToMany(mappedBy = "basketOfProducts")
    @ToString.Exclude
    private List<User> usersWhoOrdered = new ArrayList<>();
    @ManyToMany(mappedBy = "listOfLikedProducts")
    @ToString.Exclude
    private List<User> usersWhoLiked = new ArrayList<>();
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<Comment> comments = new ArrayList<>();

    public void addImages(ImageProduct image, Product element) {
        image.setElement(element);
        images.add(image);
    }
    public void calculateRating() {
        Float sum = 0.0F;
        for (Comment comment: getComments()) {
            sum += comment.getRating();
        }
        rating = sum / getComments().size();
    }
}
