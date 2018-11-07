package com.evolvestage.api.services.mail.types;

import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.HashMap;
import java.util.Map;

@Builder
@AllArgsConstructor
public class VerificationCodeEmail implements Email {
    private static final String EMAIL_ID = "583319";
    private static final String VAR_NAME_CODE = "code";

    private String to;
    private String lang;
    private String code;

    @Override
    public String getTo() {
        return to;
    }

    @Override
    public String getLang() {
        return lang;
    }

    @Override
    public Map<String, Object> getVars() {
        Map<String, Object> vars = new HashMap<>();
        vars.put(VAR_NAME_CODE, code);
        return vars;
    }

    @Override
    public String getEmailId() {
        return EMAIL_ID;
    }
}
