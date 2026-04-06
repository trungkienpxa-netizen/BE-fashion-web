package edu.thanglong.usecase.auth;

import edu.thanglong.presentation.dto.request.*;
import edu.thanglong.presentation.dto.response.AuthResponse;

public interface AuthUseCase {
    void sendRegisterOtp(SendOtpRequest request);
    AuthResponse register(RegisterRequest request);
    AuthResponse login(LoginRequest request);
    void forgotPassword(ForgotPasswordRequest request);
    void resetPassword(ResetPasswordRequest request);
    void changePassword(String userId, ChangePasswordRequest request);
}