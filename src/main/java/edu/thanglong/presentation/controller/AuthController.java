package edu.thanglong.presentation.controller;

import edu.thanglong.presentation.dto.request.*;
import edu.thanglong.presentation.dto.response.ApiResponse;
import edu.thanglong.presentation.dto.response.AuthResponse;
import edu.thanglong.usecase.auth.AuthUseCase;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
    public ApiResponse<AuthResponse> register(@Valid @RequestBody RegisterRequest request,
                                              HttpServletResponse response) {
        AuthResponse auth = authUseCase.register(request);
        setTokenCookie(response, auth.getToken());
        return ApiResponse.success(auth);
    }

    @PostMapping("/login")
    public ApiResponse<AuthResponse> login(@Valid @RequestBody LoginRequest request,
                                           HttpServletResponse response) {
        AuthResponse auth = authUseCase.login(request);
        setTokenCookie(response, auth.getToken());
        return ApiResponse.success(auth);
    }

    @PostMapping("/logout")
    public ApiResponse<Void> logout(HttpServletResponse response) {
        clearTokenCookie(response);
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

    // ─── Helpers ────────────────────────────────────────────
    private void setTokenCookie(HttpServletResponse response, String token) {
        Cookie cookie = new Cookie("jwt", token);
        cookie.setHttpOnly(true);   // JS không đọc được — chống XSS
        cookie.setSecure(false);    // đổi thành true khi dùng HTTPS
        cookie.setPath("/");
        cookie.setMaxAge(24 * 60 * 60); // 1 ngày, khớp với jwtConfig.expirationMs
        response.addCookie(cookie);
    }

    private void clearTokenCookie(HttpServletResponse response) {
        Cookie cookie = new Cookie("jwt", "");
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(0);        // xóa cookie ngay lập tức
        response.addCookie(cookie);
    }
}