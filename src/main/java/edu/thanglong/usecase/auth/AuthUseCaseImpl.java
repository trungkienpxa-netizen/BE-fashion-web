package edu.thanglong.usecase.auth;

import edu.thanglong.domain.exception.BusinessRuleException;
import edu.thanglong.domain.exception.ResourceNotFoundException;
import edu.thanglong.domain.model.*;
import edu.thanglong.domain.repository.*;
import edu.thanglong.infrastructure.service.EmailService;
import edu.thanglong.presentation.dto.request.*;
import edu.thanglong.presentation.dto.response.AuthResponse;
import edu.thanglong.presentation.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class AuthUseCaseImpl implements AuthUseCase {

    private final UserRepository         userRepository;
    private final OtpTokenRepository     otpTokenRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder        passwordEncoder;
    private final JwtTokenProvider       jwtTokenProvider;
    private final EmailService           emailService;

    @Value("${jwt.refresh-expiration-ms:604800000}")
    private long refreshExpirationMs;   // 7 ngày

    // ─── Gửi OTP đăng ký ────────────────────────────────────
    @Override
    public void sendRegisterOtp(SendOtpRequest req) {
        if (userRepository.existsByEmail(req.getEmail()))
            throw new BusinessRuleException("Email đã được sử dụng: " + req.getEmail());
        String otp = issueOtp(req.getEmail(), OtpToken.OtpType.REGISTER);
        emailService.sendOtp(req.getEmail(), otp, "Mã OTP xác thực đăng ký tài khoản");
    }

    // ─── Đăng ký ────────────────────────────────────────────
    @Override
    public AuthResponse register(RegisterRequest req) {
        if (userRepository.existsByEmail(req.getEmail()))
            throw new BusinessRuleException("Email đã được sử dụng: " + req.getEmail());

        verifyOtp(req.getEmail(), req.getOtp(), OtpToken.OtpType.REGISTER);

        User user = User.builder()
                .email(req.getEmail())
                .passwordHash(passwordEncoder.encode(req.getPassword()))
                .fullName(req.getFullName())
                .phone(req.getPhone())
                .role(User.Role.CUSTOMER)
                .status(User.Status.ACTIVE)
                .createdAt(LocalDateTime.now())
                .build();

        User saved = userRepository.save(user);
        otpTokenRepository.deleteByEmailAndType(req.getEmail(), OtpToken.OtpType.REGISTER);
        return buildAuthResponse(saved);
    }

    // ─── Đăng nhập ──────────────────────────────────────────
    @Override
    public AuthResponse login(LoginRequest req) {
        User user = userRepository.findByEmail(req.getEmail())
                .orElseThrow(() -> new BusinessRuleException("Email hoặc mật khẩu không đúng"));

        if (!passwordEncoder.matches(req.getPassword(), user.getPasswordHash()))
            throw new BusinessRuleException("Email hoặc mật khẩu không đúng");

        if (user.getStatus() == User.Status.INACTIVE)
            throw new BusinessRuleException("Tài khoản đã bị vô hiệu hóa");

        // Xóa refresh token cũ
        refreshTokenRepository.deleteByUserId(user.getId());
        return buildAuthResponse(user);
    }

    // ─── Refresh Token ───────────────────────────────────────
    @Override
    public AuthResponse refreshToken(String refreshTokenValue) {
        RefreshToken refreshToken = refreshTokenRepository.findByToken(refreshTokenValue)
                .orElseThrow(() -> new BusinessRuleException("Refresh token không hợp lệ"));

        if (refreshToken.isRevoked())
            throw new BusinessRuleException("Refresh token đã bị thu hồi");

        if (refreshToken.isExpired()) {
            refreshTokenRepository.deleteByUserId(refreshToken.getUserId());
            throw new BusinessRuleException("Refresh token đã hết hạn, vui lòng đăng nhập lại");
        }

        User user = userRepository.findById(refreshToken.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User", refreshToken.getUserId()));

        if (user.getStatus() == User.Status.INACTIVE)
            throw new BusinessRuleException("Tài khoản đã bị vô hiệu hóa");

        // Thu hồi refresh token cũ, tạo mới
        refreshToken.setRevoked(true);
        refreshTokenRepository.save(refreshToken);

        return buildAuthResponse(user);
    }

    // ─── Logout ─────────────────────────────────────────────
    @Override
    public void logout(String userId) {
        refreshTokenRepository.revokeAllByUserId(userId);
    }

    // ─── Quên mật khẩu ──────────────────────────────────────
    @Override
    public void forgotPassword(ForgotPasswordRequest req) {
        userRepository.findByEmail(req.getEmail()).ifPresent(user -> {
            String otp = issueOtp(req.getEmail(), OtpToken.OtpType.FORGOT_PASSWORD);
            emailService.sendOtp(req.getEmail(), otp, "Mã OTP đặt lại mật khẩu");
        });
    }

    // ─── Reset mật khẩu ─────────────────────────────────────
    @Override
    public void resetPassword(ResetPasswordRequest req) {
        verifyOtp(req.getEmail(), req.getOtp(), OtpToken.OtpType.FORGOT_PASSWORD);

        User user = userRepository.findByEmail(req.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User", req.getEmail()));

        user.setPasswordHash(passwordEncoder.encode(req.getNewPassword()));
        userRepository.save(user);
        otpTokenRepository.deleteByEmailAndType(req.getEmail(), OtpToken.OtpType.FORGOT_PASSWORD);
        refreshTokenRepository.deleteByUserId(user.getId());
    }

    // ─── Đổi mật khẩu ───────────────────────────────────────
    @Override
    public void changePassword(String userId, ChangePasswordRequest req) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", userId));

        if (!passwordEncoder.matches(req.getOldPassword(), user.getPasswordHash()))
            throw new BusinessRuleException("Mật khẩu cũ không đúng");

        if (req.getNewPassword().equals(req.getOldPassword()))
            throw new BusinessRuleException("Mật khẩu mới không được trùng mật khẩu cũ");

        user.setPasswordHash(passwordEncoder.encode(req.getNewPassword()));
        userRepository.save(user);
        refreshTokenRepository.deleteByUserId(userId);
    }

    // ─── Helpers ─────────────────────────────────────────────
    private String issueOtp(String email, OtpToken.OtpType type) {
        otpTokenRepository.deleteByEmailAndType(email, type);
        String otp = String.format("%06d", new Random().nextInt(1_000_000));
        otpTokenRepository.save(OtpToken.builder()
                .email(email)
                .otp(otp)
                .type(type)
                .expiredAt(LocalDateTime.now().plusMinutes(5))
                .used(false)
                .build());
        return otp;
    }

    private void verifyOtp(String email, String inputOtp, OtpToken.OtpType type) {
        OtpToken token = otpTokenRepository.findLatestByEmailAndType(email, type)
                .orElseThrow(() -> new BusinessRuleException("OTP không hợp lệ"));

        if (token.isUsed())    throw new BusinessRuleException("OTP đã được sử dụng");
        if (token.isExpired()) throw new BusinessRuleException("OTP đã hết hạn");
        if (!token.getOtp().equals(inputOtp))
            throw new BusinessRuleException("OTP không đúng");

        token.setUsed(true);
        otpTokenRepository.save(token);
    }

    private AuthResponse buildAuthResponse(User user) {
        String accessToken  = jwtTokenProvider.generateAccessToken(
            user.getId(), user.getRole().name());
        String refreshToken = jwtTokenProvider.generateRefreshToken();

        refreshTokenRepository.save(RefreshToken.builder()
                .userId(user.getId())
                .token(refreshToken)
                .expiredAt(LocalDateTime.now().plusSeconds(refreshExpirationMs / 1000))
                .revoked(false)
                .build());

        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .userId(user.getId())
                .email(user.getEmail())
                .fullName(user.getFullName())
                .role(user.getRole().name())
                .build();
    }
}