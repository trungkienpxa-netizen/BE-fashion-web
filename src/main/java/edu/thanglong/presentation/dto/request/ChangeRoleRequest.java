package edu.thanglong.presentation.dto.request;

import edu.thanglong.domain.model.User;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ChangeRoleRequest {
    @NotNull
    private User.Role role;
}