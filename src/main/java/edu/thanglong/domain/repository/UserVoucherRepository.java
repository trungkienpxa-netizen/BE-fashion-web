package edu.thanglong.domain.repository;

import edu.thanglong.domain.model.*;
import java.util.List;
import java.util.Optional;

public interface UserVoucherRepository {
    UserVoucher save(UserVoucher userVoucher);
    List<UserVoucher> findByUserId(String userId);
    Optional<UserVoucher> findByUserIdAndDiscountId(String userId, String discountId);
	boolean existsByUserIdAndDiscountId(String userId, String discountId);
}