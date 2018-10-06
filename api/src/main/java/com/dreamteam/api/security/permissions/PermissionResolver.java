package com.dreamteam.api.security.permissions;

import com.dreamteam.api.security.UserAuthentication;

import java.io.Serializable;

public interface PermissionResolver {
    String getTargetType();

    boolean hasPrivilege(UserAuthentication authentication, Serializable target);
}
