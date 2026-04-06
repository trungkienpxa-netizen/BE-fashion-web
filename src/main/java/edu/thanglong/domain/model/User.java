package edu.thanglong.domain.model;

import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter @Builder
@NoArgsConstructor @AllArgsConstructor
public class User {

    private String id;
    private String email;
    private String passwordHash;
    private String fullName;
    private String phone;
    private Role role;
    private Status status;
    private List<Address> addresses;
    private LocalDateTime createdAt;

    public enum Role   { ADMIN, STAFF, CUSTOMER }
    public enum Status { ACTIVE, INACTIVE }

    @Getter @Setter @Builder
    @NoArgsConstructor @AllArgsConstructor
    public static class Address {
        private String recipientName;
        private String phone;
        private String province;
        private String district;
        private String detail;
        private boolean isDefault;
    }
}