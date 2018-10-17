package com.evolvestage.api.security.permissions;

import com.evolvestage.api.security.UserAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class CustomPermissionEvaluator implements PermissionEvaluator {

    private Map<String, PermissionResolver> resolvers;

    @Autowired
    public CustomPermissionEvaluator(List<PermissionResolver> resolvers) {
        this.resolvers = resolvers.stream()
                .collect(Collectors.toMap(PermissionResolver::getTargetType, Function.identity()));
    }

    @Override
    public boolean hasPermission(Authentication auth, Object targetObj, Object permission) {

        if (Objects.isNull(auth) || Objects.isNull(targetObj)) {
            return false;
        }

        if (!(auth instanceof UserAuthentication)) {
            return false;
        }

        String targetPermission = String.format("%s.%s", permission, targetObj);
        return hasPermission(targetPermission);
    }

    @Override
    public boolean hasPermission(Authentication auth, Serializable targetObj, String targetType, Object permission) {
        if (Objects.isNull(auth) || Objects.isNull(targetObj)) {
            return false;
        }

        if (!(auth instanceof UserAuthentication)) {
            return false;
        }

        boolean hasPermission = hasPermission(permission);
        PermissionResolver resolver = resolvers.getOrDefault(targetType, null);
        return hasPermission && Objects.nonNull(resolver)
                && resolver.hasPrivilege((UserAuthentication) auth, targetObj);
    }

    public boolean hasPermission(Object permission) {
        return SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getAuthorities()
                .stream()
                .anyMatch(item -> Objects.equals(item.getAuthority(), permission));
    }
}
