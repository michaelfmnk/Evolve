package com.evolvestage.api.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy={})
@NotBlank
@Pattern(regexp="#[0-9a-fA-F]+")
public @interface ValidHexColor {
    String message() default "{invalid.color}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
