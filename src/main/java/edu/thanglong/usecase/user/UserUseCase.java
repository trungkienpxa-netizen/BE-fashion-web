package edu.thanglong.usecase.user;

import edu.thanglong.domain.model.User;
import edu.thanglong.presentation.dto.request.UpdateUserRequest;
import java.util.List;

public interface UserUseCase {
    // Profile (bản thân)
    User getById(String id);
    User update(String id, UpdateUserRequest request);

    // Admin — quản lý người dùng
    List<User> getAllUsers();
    User changeRole(String targetId, User.Role newRole);
    User setStatus(String targetId, User.Status newStatus);
}