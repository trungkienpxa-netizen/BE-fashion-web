package edu.thanglong.usecase.auth;

import edu.thanglong.domain.exception.BusinessRuleException;
import edu.thanglong.domain.exception.ResourceNotFoundException;
import edu.thanglong.domain.model.OtpToken;
import edu.thanglong.domain.model.User;
import edu.thanglong.domain.repository.OtpTokenRepository;
import edu.thanglong.domain.repository.UserRepository;
import edu.thanglong.infrastructure.service.EmailService;
import edu.thanglong.presentation.dto.request.*;
import edu.thanglong.presentation.dto.response.AuthResponse;
import edu.thanglong.presentation.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class AuthUseCaseImpl implements AuthUseCase {

    private final UserRepository      userRepository;
    private final OtpTokenRepository  otpTokenRepository;
    private final PasswordEncoder     passwordEncoder;
    private final JwtTokenProvider    jwtTokenProvider;
    private final EmailService        emailService;

    // ─── Gửi OTP đăng ký ────────────────────────────────────
    @Override
    public void sendRegisterOtp(SendOtpRequest req) {
        if (userRepository.existsByEmail(req.getEmail()))
            throw new BusinessRuleException("Email đã được sử dụng: " + req.getEmail());

        issueOtp(req.getEmail(), OtpToken.OtpType.REGISTER);
        emailService.sendOtp(req.getEmail(),
            otpTokenRepository
                .findLatestByEmailAndType(req.getEmail(), OtpToken.OtpType.REGISTER)
                .map(OtpToken::getOtp).orElseThrow(),
            "Mã OTP xác thực đăng ký tài khoản");
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

        return buildAuthResponse(user);
    }

    // ─── Quên mật khẩu — gửi OTP ────────────────────────────
    @Override
    public void forgotPassword(ForgotPasswordRequest req) {
        // Không tiết lộ email tồn tại hay không vì lý do bảo mật
        userRepository.findByEmail(req.getEmail()).ifPresent(user -> {
            issueOtp(req.getEmail(), OtpToken.OtpType.FORGOT_PASSWORD);
            otpTokenRepository
                .findLatestByEmailAndType(req.getEmail(), OtpToken.OtpType.FORGOT_PASSWORD)
                .ifPresent(otp -> emailService.sendOtp(
                    req.getEmail(), otp.getOtp(), "Mã OTP đặt lại mật khẩu"));
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
    }

    // ─── Đổi mật khẩu (đã đăng nhập) ───────────────────────
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
    }

    // ─── Helpers ─────────────────────────────────────────────
    private void issueOtp(String email, OtpToken.OtpType type) {
        otpTokenRepository.deleteByEmailAndType(email, type);
        String otp = String.format("%06d", new Random().nextInt(1_000_000));
        otpTokenRepository.save(OtpToken.builder()
                .email(email)
                .otp(otp)
                .type(type)
                .expiredAt(LocalDateTime.now().plusMinutes(5))
                .used(false)
                .build());
    }

    private void verifyOtp(String email, String inputOtp, OtpToken.OtpType type) {
        OtpToken token = otpTokenRepository.findLatestByEmailAndType(email, type)
                .orElseThrow(() -> new BusinessRuleException("OTP không hợp lệ"));

        if (token.isUsed())
            throw new BusinessRuleException("OTP đã được sử dụng");
        if (token.isExpired())
            throw new BusinessRuleException("OTP đã hết hạn");
        if (!token.getOtp().equals(inputOtp))
            throw new BusinessRuleException("OTP không đúng");

        token.setUsed(true);
        otpTokenRepository.save(token);
    }

    private AuthResponse buildAuthResponse(User user) {
        return AuthResponse.builder()
                .token(jwtTokenProvider.generate(user.getId(), user.getRole().name()))
                .userId(user.getId())
                .email(user.getEmail())
                .fullName(user.getFullName())
                .role(user.getRole().name())
                .build();
    }
}