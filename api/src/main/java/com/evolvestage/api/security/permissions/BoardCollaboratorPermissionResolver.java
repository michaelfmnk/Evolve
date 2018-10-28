package com.evolvestage.api.security.permissions;

import com.evolvestage.api.entities.Board;
import com.evolvestage.api.entities.User;
import com.evolvestage.api.security.UserAuthentication;
import com.evolvestage.api.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import static org.apache.commons.collections4.ListUtils.emptyIfNull;

/**
 * use as @PreAuthorize("hasPermission(#id, 'BOARD_COLLABORATOR', 'USER')")
 */

@Component
@AllArgsConstructor
public class BoardCollaboratorPermissionResolver implements PermissionResolver {

    private static final String BOARD_COLLABORATOR = "BOARD_COLLABORATOR";
    private final UserService userService;

    @Override
    public String getTargetType() {
        return BOARD_COLLABORATOR;
    }

    @Override
    public boolean hasPrivilege(UserAuthentication auth, Serializable target) {
        if (!(target instanceof Integer)) {
            return false;
        }

        User user = userService.findValidUserById(auth.getId());
        List<Board> own = emptyIfNull(user.getOwnBoards());
        List<Board> joined = emptyIfNull(user.getJoinedBoards());
        return Stream.concat(own.stream(), joined.stream())
                .map(Board::getBoardId)
                .anyMatch(id -> Objects.equals(target, id));

    }
}
