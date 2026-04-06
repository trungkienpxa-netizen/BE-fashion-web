package edu.thanglong.usecase.product;

import edu.thanglong.domain.model.Product;
import edu.thanglong.presentation.dto.request.CreateProductRequest;
import edu.thanglong.presentation.dto.request.UpdateProductRequest;
import java.util.List;

public interface ProductUseCase {
    // Public
    Product getById(String id);
    List<Product> getAll();
    List<Product> search(String keyword);
    List<Product> getByCategoryId(String categoryId);

    // Staff & Admin
    Product create(CreateProductRequest request);
    Product update(String id, UpdateProductRequest request);
    void delete(String id);
}