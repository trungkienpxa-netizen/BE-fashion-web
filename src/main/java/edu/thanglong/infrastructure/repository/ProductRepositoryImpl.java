package edu.thanglong.infrastructure.repository;

import edu.thanglong.domain.model.Product;
import edu.thanglong.domain.repository.ProductRepository;
import edu.thanglong.infrastructure.mapper.ProductMapper;
import edu.thanglong.infrastructure.repository.mongo.ProductMongoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepository {

    private final ProductMongoRepository mongo;
    private final ProductMapper mapper;

    @Override
    public Product save(Product product) {
        return mapper.toDomain(mongo.save(mapper.toEntity(product)));
    }

    @Override
    public Optional<Product> findById(String id) {
        return mongo.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<Product> findAll() {
        return mongo.findAll().stream().map(mapper::toDomain).toList();
    }

    @Override
    public List<Product> findByCategoryId(String categoryId) {
        return mongo.findByCategoryId(categoryId).stream()
                .map(mapper::toDomain).toList();
    }

    @Override
    public List<Product> findByIds(List<String> ids) {
        return mongo.findByIdIn(ids).stream()
                .map(mapper::toDomain).toList();
    }

    @Override
    public List<Product> searchByKeyword(String keyword) {
        return mongo.searchByKeyword(keyword).stream()
                .map(mapper::toDomain).toList();
    }

    @Override
    public void deleteById(String id) {
        mongo.deleteById(id);
    }
}