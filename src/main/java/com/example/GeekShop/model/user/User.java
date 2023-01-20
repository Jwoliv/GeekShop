package com.example.GeekShop.model.user;

import com.example.GeekShop.model.Product;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
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
    @Transient
    private String repeatPassword;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_basket_products",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "basket_product_id")
    )
    @ToString.Exclude
    private List<Product> basketOfProducts = new ArrayList<>();
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_liked_product",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "liked_product_id")
    )
    @ToString.Exclude
    private List<Product> listOfLikedProducts = new ArrayList<>();
}
