package edu.thanglong.usecase.discount;

import edu.thanglong.domain.exception.BusinessRuleException;
import edu.thanglong.domain.exception.ResourceNotFoundException;
import edu.thanglong.domain.model.Discount;
import edu.thanglong.domain.repository.DiscountRepository;
import edu.thanglong.domain.repository.OrderRepository;
import edu.thanglong.presentation.dto.request.CreateDiscountRequest;
import edu.thanglong.presentation.dto.request.ValidateDiscountRequest;
import edu.thanglong.presentation.dto.response.DiscountValidateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DiscountUseCaseImpl implements DiscountUseCase {

    private final DiscountRepository discountRepository;
    private final OrderRepository    orderRepository;

    @Override
    public DiscountValidateResponse validate(String userId, ValidateDiscountRequest req) {
        Discount discount = discountRepository.findByCode(req.getCode())
                .orElseThrow(() -> new BusinessRuleException(
                    "Mã giảm giá không tồn tại: " + req.getCode()));

        boolean isFirstOrder = orderRepository.findByUserId(userId).isEmpty();

        if (!discount.isValid(req.getOrderTotal(), isFirstOrder))
            throw new BusinessRuleException("Mã giảm giá không hợp lệ hoặc không đủ điều kiện");

        return DiscountValidateResponse.builder()
                .discountId(discount.getId())
                .code(discount.getCode())
                .discountAmount(discount.calculateDiscount(req.getOrderTotal()))
                .build();
    }

    @Override
    public Discount create(CreateDiscountRequest req) {
        if (discountRepository.findByCode(req.getCode()).isPresent())
            throw new BusinessRuleException("Mã giảm giá đã tồn tại: " + req.getCode());

        return discountRepository.save(Discount.builder()
                .code(req.getCode().toUpperCase())
                .type(req.getType())
                .value(req.getValue())
                .maxDiscountAmount(req.getMaxDiscountAmount())
                .minOrderValue(req.getMinOrderValue())
                .usageLimit(req.getUsageLimit())
                .usedCount(0)
                .firstOrderOnly(req.isFirstOrderOnly())
                .startDate(req.getStartDate())
                .endDate(req.getEndDate())
                .status(Discount.DiscountStatus.ACTIVE)
                .build());
    }

    @Override
    public Discount getById(String id) {
        return discountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Discount", id));
    }

    @Override
    public List<Discount> getAll() {
        return discountRepository.findAllActive();
    }

    @Override
    public void delete(String id) {
        Discount discount = getById(id);
        discount.setStatus(Discount.DiscountStatus.DISABLED);
        discountRepository.save(discount);
    }
}