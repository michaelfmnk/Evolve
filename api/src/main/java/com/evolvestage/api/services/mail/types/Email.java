package com.evolvestage.api.services.mail.types;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class Email {
    protected String to;
    protected String lang;
    protected Map<String, Object> vars;

    public abstract String getEmailId();
}
