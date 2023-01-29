package com.example.GeekShop.model.order;

import com.example.GeekShop.model.product.Product;
import com.example.GeekShop.model.user.User;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Component
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "_order")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn
    private User user;
    private Date dateOfCreate;
    @Enumerated(EnumType.STRING)
    private StatusOfOrder statusOfOrder;
    @ManyToMany(mappedBy = "orders", cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @ToString.Exclude
    private List<Product> productsOfOrders = new ArrayList<>();
}
