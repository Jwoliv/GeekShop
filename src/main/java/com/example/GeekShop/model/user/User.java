package com.example.GeekShop.model.user;

import com.example.GeekShop.model.message.Message;
import com.example.GeekShop.model.order.Order;
import com.example.GeekShop.model.product.Comment;
import com.example.GeekShop.model.product.Product;
import com.example.GeekShop.model.product.ProductByBasket;
import com.example.GeekShop.model.product.SizeOfProduct;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Entity
@Component
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String firstname;
    @NotBlank
    private String lastname;
    @Enumerated(EnumType.STRING)
    private Role role;
    @NotBlank
    @Email
    @Column(unique = true)
    private String email;
    private boolean active;
    @NotBlank
    private String password;
    @Min(value = 0)
    private Integer spendMoney;
    @Min(value = 0)
    private Integer bonusPoints;
    @Enumerated(EnumType.STRING)
    private LevelSupport levelSupport;
    @Transient
    private String repeatPassword;
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(
            name = "user_basket_products",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "basket_product_id")
    )
    @ToString.Exclude
    private List<ProductByBasket> basketOfProducts = new ArrayList<>();
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(
            name = "user_liked_product",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "liked_product_id")
    )
    @ToString.Exclude
    private List<Product> listOfLikedProducts = new ArrayList<>();
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(
            name = "user_view_product",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "view_product_id")
    )
    @ToString.Exclude
    private List<Product> viewedProducts = new ArrayList<>();
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<Comment> comments = new ArrayList<>();
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<Order> orders = new ArrayList<>();
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<Message> messages = new ArrayList<>();


    public String getFullName() {
        return firstname + " " + lastname;
    }
    public void selectLevelOfSupport() {
        if (this.spendMoney <= 4000) this.levelSupport = LevelSupport.Start;
        else if (this.spendMoney <= 10500) this.levelSupport = LevelSupport.Standard;
        else if (this.spendMoney <= 20000) this.levelSupport = LevelSupport.Ultra;
        else this.levelSupport = LevelSupport.UltraPlus;
    }
    public int getTotalBillOfBasket() {
        int totalBill = 0;
        for (ProductByBasket product: this.basketOfProducts) {
            if (product.getProduct().getDiscount() != 0) {
                totalBill += product.getNumberProduct() * (product.getProduct().getPrice()  - product.getProduct().getPrice() * product.getProduct().getDiscount() / 100);
                continue;
            }
            totalBill +=  product.getNumberProduct() *  product.getProduct().getPrice();
        }
        return totalBill - this.bonusPoints;
    }
    public ProductByBasket getProductByBasketIfItExist(Product product, SizeOfProduct sizeOfProduct) {
        for (ProductByBasket productByUser: getBasketOfProducts()) {
            if (productByUser.getProduct().equals(product) && productByUser.getSize() == sizeOfProduct) {
                return productByUser;
            }
        }
        return null;
    }
}
