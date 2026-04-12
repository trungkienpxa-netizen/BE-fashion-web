package edu.thanglong.presentation.controller;

import edu.thanglong.presentation.dto.request.*;
import edu.thanglong.presentation.dto.response.ApiResponse;
import edu.thanglong.presentation.dto.response.AuthResponse;
import edu.thanglong.usecase.auth.AuthUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthUseCase authUseCase;

    @PostMapping("/send-register-otp")
    public ApiResponse<Void> sendRegisterOtp(@Valid @RequestBody SendOtpRequest request) {
        authUseCase.sendRegisterOtp(request);
        return ApiResponse.success(null);
    }

    @PostMapping("/register")
    public ApiResponse<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        return ApiResponse.success(authUseCase.register(request));
    }

    @PostMapping("/login")
    public ApiResponse<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        return ApiResponse.success(authUseCase.login(request));
    }

    @PostMapping("/refresh-token")
    public ApiResponse<AuthResponse> refreshToken(
            @Valid @RequestBody RefreshTokenRequest request) {
        return ApiResponse.success(authUseCase.refreshToken(request.getRefreshToken()));
    }

    @PostMapping("/logout")
    public ApiResponse<Void> logout(Authentication authentication) {
        authUseCase.logout((String) authentication.getPrincipal());
        return ApiResponse.success(null);
    }

    @PostMapping("/forgot-password")
    public ApiResponse<Void> forgotPassword(@Valid @RequestBody ForgotPasswordRequest request) {
        authUseCase.forgotPassword(request);
        return ApiResponse.success(null);
    }

    @PostMapping("/reset-password")
    public ApiResponse<Void> resetPassword(@Valid @RequestBody ResetPasswordRequest request) {
        authUseCase.resetPassword(request);
        return ApiResponse.success(null);
    }

    @PostMapping("/change-password")
    public ApiResponse<Void> changePassword(Authentication authentication,
                                            @Valid @RequestBody ChangePasswordRequest request) {
        authUseCase.changePassword((String) authentication.getPrincipal(), request);
        return ApiResponse.success(null);
    }
}