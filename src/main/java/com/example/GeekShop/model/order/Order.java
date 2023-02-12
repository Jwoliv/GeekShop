package com.example.GeekShop.model.order;

import com.example.GeekShop.model.product.ProductByBasket;
import com.example.GeekShop.model.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Component
@Getter
@Setter
@RequiredArgsConstructor
@ToString
@AllArgsConstructor
@Table(name = "_order")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private User user;
    private Date dateOfCreate;
    private Long codeOfOrder;
    @Enumerated(EnumType.STRING)
    private StatusOfOrder statusOfOrder;
    private Integer priceOfOrder;
    @NotNull
    private String firstnameOfRecipient;
    @NotNull
    private String lastnameOfRecipient;
    @NotNull
    private String surnameOfRecipient;
    private Long codeOfInvoice;
    @NotNull
    private String numberOfPhone;
    @NotNull
    private String city;
    @Email
    @NotNull
    private String email;
    @NotNull
    private Integer numberOfPostOffice;
    @Enumerated(EnumType.STRING)
    private TypePostOffice typePostOffice;
    @Enumerated(EnumType.STRING)
    private RegionOfCountry regionOfCountry;
    @NotNull
    private Boolean isVerify;
    @ManyToMany(mappedBy = "orders", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<ProductByBasket> productsOfOrders = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Order order = (Order) o;
        return id != null && Objects.equals(id, order.id);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (dateOfCreate != null ? dateOfCreate.hashCode() : 0);
        result = 31 * result + (statusOfOrder != null ? statusOfOrder.hashCode() : 0);
        result = 31 * result + (priceOfOrder != null ? priceOfOrder.hashCode() : 0);
        result = 31 * result + (productsOfOrders != null ? productsOfOrders.hashCode() : 0);
        return result;
    }
}
