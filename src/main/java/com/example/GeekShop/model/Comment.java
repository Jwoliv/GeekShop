package com.example.GeekShop.model;

import com.example.GeekShop.model.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.stereotype.Component;

@Entity
@Component
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private User user;
    @ManyToOne
    private Product product;
    @NotBlank
    @Size(max = 400)
    private String text;
    @NotNull
    @Max(value = 5)
    @Min(value = 0)
    private Integer rating;
}
