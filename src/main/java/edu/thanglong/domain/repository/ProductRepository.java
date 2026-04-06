package edu.thanglong.domain.repository;

import edu.thanglong.domain.model.Product;
import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    Product save(Product product);
    Optional<Product> findById(String id);
    List<Product> findAll();
    List<Product> findByCategoryId(String categoryId);
    List<Product> findByIds(List<String> ids);
    List<Product> searchByKeyword(String keyword);
    void deleteById(String id);
}