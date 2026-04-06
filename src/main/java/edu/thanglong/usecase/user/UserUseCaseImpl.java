package edu.thanglong.usecase.user;

import edu.thanglong.domain.exception.BusinessRuleException;
import edu.thanglong.domain.exception.ResourceNotFoundException;
import edu.thanglong.domain.model.User;
import edu.thanglong.domain.repository.UserRepository;
import edu.thanglong.presentation.dto.request.UpdateUserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserUseCaseImpl implements UserUseCase {

    private final UserRepository userRepository;

    @Override
    public User getById(String id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", id));
    }

    @Override
    public User update(String id, UpdateUserRequest req) {
        User user = getById(id);
        if (req.getFullName() != null) user.setFullName(req.getFullName());
        if (req.getPhone()    != null) user.setPhone(req.getPhone());
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User changeRole(String targetId, User.Role newRole) {
        User user = getById(targetId);
        if (user.getRole() == newRole)
            throw new BusinessRuleException("Người dùng đã có role: " + newRole);
        user.setRole(newRole);
        return userRepository.save(user);
    }

    @Override
    public User setStatus(String targetId, User.Status newStatus) {
        User user = getById(targetId);
        if (user.getStatus() == newStatus)
            throw new BusinessRuleException("Tài khoản đã ở trạng thái: " + newStatus);
        user.setStatus(newStatus);
        return userRepository.save(user);
    }
}