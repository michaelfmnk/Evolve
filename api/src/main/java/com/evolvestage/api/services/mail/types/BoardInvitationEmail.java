package com.evolvestage.api.services.mail.types;

import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.HashMap;
import java.util.Map;

@Builder
@AllArgsConstructor
public class BoardInvitationEmail implements Email {
    private static final String EMAIL_ID = "583160";
    private static final String VAR_NAME_LINK = "link";
    private static final String VAR_NAME_BOARD_NAME = "boardName";

    private String to;
    private String lang;
    private String boardName;
    private String link;

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
        vars.put(VAR_NAME_LINK, link);
        vars.put(VAR_NAME_BOARD_NAME, boardName);
        return vars;
    }

    @Override
    public String getEmailId() {
        return EMAIL_ID;
    }
}
