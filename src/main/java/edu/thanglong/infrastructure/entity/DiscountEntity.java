package edu.thanglong.infrastructure.entity;

import edu.thanglong.domain.model.Discount;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import java.math.BigDecimal;
import java.time.LocalDate;

@Document(collection = "discounts")
@Getter @Setter @Builder
@NoArgsConstructor @AllArgsConstructor
public class DiscountEntity {

    @Id
    private String id;

    @Indexed(unique = true)
    private String code;

    private Discount.DiscountType type;
    private BigDecimal value;
    private BigDecimal maxDiscountAmount;
    private BigDecimal minOrderValue;
    private int usageLimit;
    private int usedCount;
    private boolean firstOrderOnly;
    private LocalDate startDate;
    private LocalDate endDate;
    private Discount.DiscountStatus status;
}