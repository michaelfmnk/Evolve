package com.evolvestage.api.utils;

import lombok.experimental.UtilityClass;
import org.springframework.security.core.context.SecurityContextHolder;

@UtilityClass
public class SecurityUtils {

    public Integer getUserIdFromSecurityContext() {
        return (Integer) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
