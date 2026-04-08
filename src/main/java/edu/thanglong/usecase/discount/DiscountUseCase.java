package edu.thanglong.usecase.discount;

import edu.thanglong.domain.model.Discount;
import edu.thanglong.presentation.dto.request.CreateDiscountRequest;
import edu.thanglong.presentation.dto.request.ValidateDiscountRequest;
import edu.thanglong.presentation.dto.response.DiscountValidateResponse;
import java.util.List;

public interface DiscountUseCase {
    // Public
    DiscountValidateResponse validate(String userId, ValidateDiscountRequest request);

    // Admin
    Discount create(CreateDiscountRequest request);
    Discount getById(String id);
    List<Discount> getAll();
    void delete(String id);
}