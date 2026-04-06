package edu.thanglong.domain.repository;

import edu.thanglong.domain.model.Discount;
import java.util.List;
import java.util.Optional;

public interface DiscountRepository {
    Discount save(Discount discount);
    Optional<Discount> findById(String id);
    Optional<Discount> findByCode(String code);
    List<Discount> findAllActive();
}