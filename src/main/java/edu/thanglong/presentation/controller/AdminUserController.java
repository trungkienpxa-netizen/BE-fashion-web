package edu.thanglong.presentation.controller;

import edu.thanglong.presentation.dto.request.ChangeRoleRequest;
import edu.thanglong.presentation.dto.request.ChangeStatusRequest;
import edu.thanglong.presentation.dto.response.ApiResponse;
import edu.thanglong.presentation.dto.response.UserResponse;
import edu.thanglong.usecase.user.UserUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/users")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
public class AdminUserController {

    private final UserUseCase userUseCase;

    @GetMapping
    public ApiResponse<List<UserResponse>> getAllUsers() {
        List<UserResponse> list = userUseCase.getAllUsers()
                .stream().map(UserResponse::from).toList();
        return ApiResponse.success(list);
    }

    @GetMapping("/{id}")
    public ApiResponse<UserResponse> getById(@PathVariable String id) {
        return ApiResponse.success(UserResponse.from(userUseCase.getById(id)));
    }

    @PatchMapping("/{id}/role")
    public ApiResponse<UserResponse> changeRole(@PathVariable String id,
                                                @Valid @RequestBody ChangeRoleRequest request) {
        return ApiResponse.success(
            UserResponse.from(userUseCase.changeRole(id, request.getRole()))
        );
    }

    @PatchMapping("/{id}/status")
    public ApiResponse<UserResponse> changeStatus(@PathVariable String id,
                                                  @Valid @RequestBody ChangeStatusRequest request) {
        return ApiResponse.success(
            UserResponse.from(userUseCase.setStatus(id, request.getStatus()))
        );
    }
}