package com.evolvestage.api.config.ws;

import com.evolvestage.api.security.UserAuthentication;
import com.evolvestage.api.security.permissions.BoardCollaboratorPermissionResolver;
import com.evolvestage.api.services.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Objects;

@Component
@CommonsLog
@Transactional
@AllArgsConstructor
public class WebSocketAuthChannelInterceptor implements ChannelInterceptor {
    private final BoardCollaboratorPermissionResolver permissionResolver;
    private final UserService userService;

    public static final String AUTH_HEADER = "simpUser";

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(message);

        if (StompCommand.CONNECT.equals(headerAccessor.getCommand())
                && isAuthenticated(headerAccessor)) {
            UserAuthentication userToken
                    = (UserAuthentication) headerAccessor.getHeader(AUTH_HEADER);
            if (Objects.isNull(userToken)) {
                throw new IllegalArgumentException("User is not authenticated");
            }
        }

        if (StompCommand.SUBSCRIBE.equals(headerAccessor.getCommand())
                && isAuthenticated(headerAccessor)) {
            UserAuthentication userToken
                    = (UserAuthentication) headerAccessor.getHeader(AUTH_HEADER);
            if (!canUserAccess(userToken, headerAccessor.getDestination())) {
                throw new IllegalArgumentException("User has no permission to access destination " +
                        headerAccessor.getDestination());
            }
        }
        return message;
    }

    private boolean canUserAccess(UserAuthentication userToken, String destination) {
        Integer boardId = WebSocketChannels.getBoardIdFromChannelName(destination);
        return permissionResolver.hasPrivilege(userToken, boardId);
    }

    private boolean isAuthenticated(StompHeaderAccessor headerAccessor) {
        return Objects.nonNull(headerAccessor.getHeader(AUTH_HEADER))
                &&  headerAccessor.getHeader(AUTH_HEADER) instanceof UserAuthentication;
    }



}
