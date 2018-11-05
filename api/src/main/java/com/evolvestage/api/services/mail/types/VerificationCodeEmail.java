package com.evolvestage.api.services.mail.types;

import lombok.Builder;

import java.util.HashMap;

public class VerificationCodeEmail extends Email {

    private final String EMAIL_ID = "583319";
    private final String VAR_NAME_CODE = "code";

    @Builder
    public VerificationCodeEmail(String to, String lang, String code) {
        super(to, lang, new HashMap<>());
        vars.put(VAR_NAME_CODE, code);
    }

    @Override
    public String getEmailId() {
        return EMAIL_ID;
    }
}
