package edu.thanglong.presentation.controller;

import edu.thanglong.presentation.dto.response.ApiResponse;
import edu.thanglong.presentation.dto.response.NotificationResponse;
import edu.thanglong.usecase.notification.NotificationUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationUseCase notificationUseCase;

    @GetMapping
    public ApiResponse<List<NotificationResponse>> getAll(Authentication authentication) {
        return ApiResponse.success(
            notificationUseCase.getMyNotifications((String) authentication.getPrincipal())
                .stream().map(NotificationResponse::from).toList()
        );
    }

    @GetMapping("/unread-count")
    public ApiResponse<Long> countUnread(Authentication authentication) {
        return ApiResponse.success(
            notificationUseCase.countUnread((String) authentication.getPrincipal())
        );
    }

    @PatchMapping("/mark-all-read")
    public ApiResponse<Void> markAllRead(Authentication authentication) {
        notificationUseCase.markAllRead((String) authentication.getPrincipal());
        return ApiResponse.success(null);
    }
}