package com.evolvestage.api.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;
import java.util.regex.Pattern;

public class PasswordValidator implements ConstraintValidator<ValidPassword, String> {
    private final static String PASSWORD_PATTERN = "(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,}";
    private final static int PASSWORD_MIN_LEN = 8;
    @Override
    public boolean isValid(String password, ConstraintValidatorContext constraintValidatorContext) {
        return Objects.nonNull(password)
                && password.length() >= PASSWORD_MIN_LEN
                && Pattern.matches(PASSWORD_PATTERN, password);
    }
}
