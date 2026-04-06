package edu.thanglong.domain.repository;

import edu.thanglong.domain.model.*;
import java.util.Optional;

public interface CartRepository {
    Cart save(Cart cart);
    Optional<Cart> findByUserId(String userId);
    void deleteByUserId(String userId);
}