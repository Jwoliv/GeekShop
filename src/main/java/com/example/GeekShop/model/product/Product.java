package com.example.GeekShop.model.product;

import com.example.GeekShop.model.images.ImageProduct;
import com.example.GeekShop.model.product_fields.Category;
import com.example.GeekShop.model.product_fields.Season;
import com.example.GeekShop.model.product_fields.Theme;
import com.example.GeekShop.model.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@Component
@EqualsAndHashCode
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
    private Float rating;
    private Long previewsId;
    @Size(min = 10, max = 500)
    private String describe;
    @ManyToOne
    @JoinColumn(name = "theme_id")
    private Theme theme;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
    @ManyToOne
    @JoinColumn(name = "season_id")
    private Season season;
    @Enumerated(EnumType.STRING)
    private Color color;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @ElementCollection(targetClass = SizeOfProduct.class)
    @CollectionTable(name = "product_size", joinColumns = @JoinColumn(name = "product_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "size_id")
    private List<SizeOfProduct> sizes = new ArrayList<>();
    @OneToMany(mappedBy = "element", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<ImageProduct> images = new ArrayList<>();
    @ManyToMany(mappedBy = "basketOfProducts", cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @ToString.Exclude
    private List<User> usersWhoAddedToBasket = new ArrayList<>();
    @ManyToMany(mappedBy = "listOfLikedProducts", cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @ToString.Exclude
    private List<User> usersWhoLiked = new ArrayList<>();
    @ManyToMany(mappedBy = "viewedProducts", cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @ToString.Exclude
    private List<User> usersWhoViewed = new ArrayList<>();
    @ManyToMany(mappedBy = "orders", cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @ToString.Exclude
    private List<User> usersWhoOrdered = new ArrayList<>();
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
        rating = Math.round(rating * 100.0F) / 100.0F;
        setRating(rating);
    }
    public void getSizeForCheck(List<SizeOfProduct> sizes) {
        sizes.forEach(this::addSizeToCollection);
    }
    public void addSizeToCollection(SizeOfProduct size) {
        if (size != null) {
            this.getSizes().add(size);
        }
    }
    public Integer priceAfterDiscount() {
        if (discount != 0) return price - (price * discount / 100);
        return price;
    }
}
