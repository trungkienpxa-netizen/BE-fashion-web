package edu.thanglong.infrastructure.entity;

import edu.thanglong.domain.model.User;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "users")
@Getter @Setter @Builder
@NoArgsConstructor @AllArgsConstructor
public class UserEntity {

    @Id
    private String id;

    @Indexed(unique = true)
    private String email;

    private String passwordHash;
    private String fullName;
    private String phone;
    private User.Role role;
    private User.Status status;
    private List<User.Address> addresses;
    private LocalDateTime createdAt;
}