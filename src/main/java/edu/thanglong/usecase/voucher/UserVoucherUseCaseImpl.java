package edu.thanglong.usecase.voucher;

import edu.thanglong.domain.exception.BusinessRuleException;
import edu.thanglong.domain.exception.ResourceNotFoundException;
import edu.thanglong.domain.model.Discount;
import edu.thanglong.domain.model.UserVoucher;
import edu.thanglong.domain.repository.DiscountRepository;
import edu.thanglong.domain.repository.UserVoucherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserVoucherUseCaseImpl implements UserVoucherUseCase {

    private final UserVoucherRepository userVoucherRepository;
    private final DiscountRepository    discountRepository;

    @Override
    public List<UserVoucher> getMyVouchers(String userId) {
        return userVoucherRepository.findByUserId(userId);
    }

    @Override
    public UserVoucher saveVoucher(String userId, String discountCode) {
        Discount discount = discountRepository.findByCode(discountCode)
                .orElseThrow(() -> new ResourceNotFoundException("Discount", discountCode));

        if (discount.getStatus() != Discount.DiscountStatus.ACTIVE)
            throw new BusinessRuleException("Mã giảm giá không còn hiệu lực");

        if (userVoucherRepository.existsByUserIdAndDiscountId(userId, discount.getId()))
            throw new BusinessRuleException("Bạn đã lưu voucher này rồi");

        return userVoucherRepository.save(UserVoucher.builder()
                .userId(userId)
                .discountId(discount.getId())
                .used(false)
                .savedAt(LocalDateTime.now())
                .build());
    }
}