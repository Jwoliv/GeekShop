package com.example.GeekShop.model.message;

import com.example.GeekShop.model.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.stereotype.Component;

@Entity
@Component
@Setter
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty(message = "The title of message can't be empty")
    @Size(max = 70)
    private String title;
    @NotEmpty(message = "The text of message can't be empty")
    @Size(max = 500)
    private String text;
    @Enumerated(EnumType.STRING)
    private TypeOfMessage typeOfMessage;
    @Size(max = 500)
    private String adminAnswer;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
