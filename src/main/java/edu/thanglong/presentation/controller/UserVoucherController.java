package edu.thanglong.presentation.controller;

import edu.thanglong.presentation.dto.response.ApiResponse;
import edu.thanglong.presentation.dto.response.UserVoucherResponse;
import edu.thanglong.usecase.voucher.UserVoucherUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/vouchers")
@RequiredArgsConstructor
public class UserVoucherController {

    private final UserVoucherUseCase userVoucherUseCase;

    @GetMapping
    public ApiResponse<List<UserVoucherResponse>> getMyVouchers(Authentication authentication) {
        return ApiResponse.success(
            userVoucherUseCase.getMyVouchers((String) authentication.getPrincipal())
                .stream().map(UserVoucherResponse::from).toList()
        );
    }

    @PostMapping("/save/{discountCode}")
    public ApiResponse<UserVoucherResponse> saveVoucher(Authentication authentication,
                                                        @PathVariable String discountCode) {
        return ApiResponse.success(UserVoucherResponse.from(
            userVoucherUseCase.saveVoucher(
                (String) authentication.getPrincipal(), discountCode))
        );
    }
}