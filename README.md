# Fashion Shop API

REST API cho website bán quần áo có chức năng gợi ý sản phẩm bằng AI.
Xây dựng bằng **Java Spring Boot** theo kiến trúc **Clean Architecture**.

---

## Công nghệ sử dụng

| Công nghệ | Mục đích |
|---|---|
| Java 21 | Ngôn ngữ lập trình |
| Spring Boot 3.x | Framework chính |
| Spring Security + JWT | Xác thực & phân quyền |
| MongoDB | Cơ sở dữ liệu |
| Spring Data MongoDB | Tương tác với MongoDB |
| Lombok | Giảm boilerplate code |
| JavaMailSender | Gửi email OTP |
| Docker | Containerization |
| Render | Cloud deployment |

---

## Kiến trúc dự án
```
src/main/java/edu/thanglong/
│
├── domain/                         # Layer trong cùng — thuần Java
│   ├── model/                      # Business entities
│   │   ├── User.java
│   │   ├── Product.java
│   │   ├── Order.java
│   │   ├── Payment.java
│   │   ├── Cart.java
│   │   ├── Discount.java
│   │   ├── Review.java
│   │   ├── Notification.java
│   │   ├── UserVoucher.java
│   │   └── OtpToken.java
│   ├── repository/                 # Interfaces (contracts)
│   └── exception/                  # Domain exceptions
│
├── usecase/                        # Business logic
│   ├── auth/
│   ├── user/
│   ├── product/
│   ├── order/
│   ├── payment/
│   └── ai/
│
├── infrastructure/                 # Kết nối bên ngoài
│   ├── entity/                     # MongoDB documents
│   ├── repository/
│   │   ├── mongo/                  # Spring Data interfaces
│   │   └── impl/                   # Implementations
│   ├── mapper/                     # Domain ↔ Entity
│   ├── service/                    # Email, Cloudinary...
│   └── config/                     # Security, MongoDB...
│
└── presentation/                   # Layer ngoài cùng
    ├── controller/                 # REST endpoints
    ├── dto/
    │   ├── request/
    │   └── response/
    ├── security/                   # JWT filter
    └── handler/                    # Global exception handler
```

---