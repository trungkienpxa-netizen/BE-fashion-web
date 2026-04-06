package edu.thanglong.usecase.product;

import edu.thanglong.domain.exception.ResourceNotFoundException;
import edu.thanglong.domain.model.Product;
import edu.thanglong.domain.repository.ProductRepository;
import edu.thanglong.presentation.dto.request.CreateProductRequest;
import edu.thanglong.presentation.dto.request.UpdateProductRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductUseCaseImpl implements ProductUseCase {

    private final ProductRepository productRepository;

    @Override
    public Product getById(String id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product", id));
    }

    @Override
    public List<Product> getAll() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> search(String keyword) {
        return productRepository.searchByKeyword(keyword);
    }

    @Override
    public List<Product> getByCategoryId(String categoryId) {
        return productRepository.findByCategoryId(categoryId);
    }

    @Override
    public Product create(CreateProductRequest req) {
        Product product = Product.builder()
                .categoryId(req.getCategoryId())
                .name(req.getName())
                .description(req.getDescription())
                .basePrice(req.getBasePrice())
                .brand(req.getBrand())
                .variants(req.getVariants())
                .styles(req.getStyles())
                .occasions(req.getOccasions())
                .bodyShapes(req.getBodyShapes())
                .fitType(req.getFitType())
                .heightRange(req.getHeightRange())
                .weightRange(req.getWeightRange())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        return productRepository.save(product);
    }

    @Override
    public Product update(String id, UpdateProductRequest req) {
        Product product = getById(id);
        if (req.getName()        != null) product.setName(req.getName());
        if (req.getDescription() != null) product.setDescription(req.getDescription());
        if (req.getBasePrice()   != null) product.setBasePrice(req.getBasePrice());
        if (req.getBrand()       != null) product.setBrand(req.getBrand());
        if (req.getVariants()    != null) product.setVariants(req.getVariants());
        if (req.getStyles()      != null) product.setStyles(req.getStyles());
        if (req.getOccasions()   != null) product.setOccasions(req.getOccasions());
        if (req.getBodyShapes()  != null) product.setBodyShapes(req.getBodyShapes());
        if (req.getFitType()     != null) product.setFitType(req.getFitType());
        if (req.getHeightRange() != null) product.setHeightRange(req.getHeightRange());
        if (req.getWeightRange() != null) product.setWeightRange(req.getWeightRange());
        product.setUpdatedAt(LocalDateTime.now());
        return productRepository.save(product);
    }

    @Override
    public void delete(String id) {
        getById(id);
        productRepository.deleteById(id);
    }
}