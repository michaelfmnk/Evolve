package com.dreamteam.api.utils;

import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.RandomStringUtils;

@UtilityClass
public class CodeGenerator {
    public static String generateCode(int len) {
        return RandomStringUtils.randomNumeric(len);
    }
}
