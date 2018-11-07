package com.evolvestage.api.services.mail.types;

import java.util.Map;

public interface Email {
    String getTo();
    String getLang();
    Map<String, Object> getVars();
    String getEmailId();
}
