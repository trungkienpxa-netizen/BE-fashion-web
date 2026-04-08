package edu.thanglong.usecase.voucher;

import edu.thanglong.domain.model.UserVoucher;
import java.util.List;

public interface UserVoucherUseCase {
    List<UserVoucher> getMyVouchers(String userId);
    UserVoucher saveVoucher(String userId, String discountCode);
}