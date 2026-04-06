package edu.thanglong.presentation.dto.request;

import edu.thanglong.domain.model.Product;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.List;

@Getter @Setter
public class UpdateProductRequest {
    private String name;
    private String description;
    private BigDecimal basePrice;
    private String brand;
    private List<Product.Variant> variants;
    private List<String> styles;
    private List<String> occasions;
    private List<String> bodyShapes;
    private String fitType;
    private String heightRange;
    private String weightRange;
}