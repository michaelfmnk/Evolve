package com.evolvestage.api.security.permissions;

import com.evolvestage.api.security.UserAuthentication;

import java.io.Serializable;

public interface PermissionResolver {
    String getTargetType();

    boolean hasPrivilege(UserAuthentication authentication, Serializable target);
}
