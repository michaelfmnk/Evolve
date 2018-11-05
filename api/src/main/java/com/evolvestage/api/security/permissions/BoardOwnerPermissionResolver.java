package com.evolvestage.api.security.permissions;

import com.evolvestage.api.entities.Board;
import com.evolvestage.api.entities.User;
import com.evolvestage.api.security.UserAuthentication;
import com.evolvestage.api.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Objects;

import static org.apache.commons.collections4.ListUtils.emptyIfNull;

/**
 * use as @PreAuthorize("hasPermission(#id, 'BOARD_OWNER', 'USER')")
 */

@Component
@AllArgsConstructor
public class BoardOwnerPermissionResolver implements PermissionResolver {

    private static final String BOARD_OWNER = "BOARD_OWNER";
    private final UserService userService;

    @Override
    public String getTargetType() {
        return BOARD_OWNER;
    }

    @Override
    public boolean hasPrivilege(UserAuthentication auth, Serializable target) {
        if (!(target instanceof Integer)) {
            return false;
        }
        User user = userService.findValidUserById(auth.getId());
        return emptyIfNull(user.getOwnBoards()).stream()
                .map(Board::getBoardId)
                .anyMatch(id -> Objects.equals(target, id));
    }
}
