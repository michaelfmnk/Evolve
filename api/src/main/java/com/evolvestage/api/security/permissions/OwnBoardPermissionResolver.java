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
 * use as @PreAuthorize("hasPermission(#id, 'OWN_BOARD', 'USER')")
 */

@Component
@AllArgsConstructor
public class OwnBoardPermissionResolver implements PermissionResolver {

    private static final String OWN_BOARD = "OWN_BOARD";
    private final UserService userService;

    @Override
    public String getTargetType() {
        return OWN_BOARD;
    }

    @Override
    public boolean hasPrivilege(UserAuthentication auth, Serializable target) {
        if (!(target instanceof Integer)) {
            return false;
        }
        User user = userService.findValidUserById(auth.getId());
        List<Board> own = emptyIfNull(user.getJoinedBoards());
        List<Board> joined = emptyIfNull(user.getOwnBoards());
        return Stream.concat(own.stream(), joined.stream())
                .map(Board::getBoardId)
                .anyMatch(id -> Objects.equals(target, id));
    }
}
