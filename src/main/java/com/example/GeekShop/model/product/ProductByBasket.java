package com.example.GeekShop.model.product;

import com.example.GeekShop.model.order.Order;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@ToString
@Component
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class ProductByBasket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Product product;
    private Integer numberProduct;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "product_order",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name ="order_id")
    )
    @ToString.Exclude
    private List<Order> orders = new ArrayList<>();
    @Enumerated(EnumType.STRING)
    private SizeOfProduct size;
}
