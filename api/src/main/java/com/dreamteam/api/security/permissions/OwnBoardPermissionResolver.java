package com.dreamteam.api.security.permissions;

import com.dreamteam.api.entities.Board;
import com.dreamteam.api.entities.User;
import com.dreamteam.api.security.UserAuthentication;
import com.dreamteam.api.services.UserService;
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
