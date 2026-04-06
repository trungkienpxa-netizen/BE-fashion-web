package edu.thanglong.presentation.controller;

import edu.thanglong.presentation.dto.request.UpdateUserRequest;
import edu.thanglong.presentation.dto.response.ApiResponse;
import edu.thanglong.presentation.dto.response.UserResponse;
import edu.thanglong.usecase.user.UserUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserUseCase userUseCase;

    // Xem profile của chính mình
    @GetMapping("/me")
    public ApiResponse<UserResponse> getMe(Authentication authentication) {
        return ApiResponse.success(
            UserResponse.from(userUseCase.getById((String) authentication.getPrincipal()))
        );
    }

    // Cập nhật profile của chính mình
    @PutMapping("/me")
    public ApiResponse<UserResponse> updateMe(Authentication authentication,
                                              @RequestBody UpdateUserRequest request) {
        return ApiResponse.success(
            UserResponse.from(userUseCase.update((String) authentication.getPrincipal(), request))
        );
    }
}