package com.evolvestage.docsapi.utils;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Aspect
@Component
public class IfNullReturnNullAspect {
    @Around("@annotation(com.evolvestage.docsapi.utils.IfNullReturnNull)")
    public Object ifNullReturnNullAnnotationHandler(ProceedingJoinPoint joinPoint) throws Throwable {
        for (Object arg : joinPoint.getArgs()) {
            if (Objects.isNull(arg)) {
                return null;
            }
        }
        return joinPoint.proceed();
    }
}
