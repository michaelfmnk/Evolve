package com.evolvestage.api.utils;

import lombok.AllArgsConstructor;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
@CommonsLog
@AllArgsConstructor
public class MessagesService {
    private final MessageSource messageSource;

    public String getMessage(String key) {
        Locale locale = LocaleContextHolder.getLocale();
        try {
            return messageSource.getMessage(key, null, locale);
        } catch (Exception e) {
            log.warn(String.format("No message was found for key '%s' and locale '%s'", key, locale.toLanguageTag()));
        }
        return key;
    }

    public String getMessage(String key, String defaultMsg) {
        Locale locale = LocaleContextHolder.getLocale();
        try {
            return messageSource.getMessage(key, null, defaultMsg, locale);
        } catch (Exception e) {
            log.warn(String.format("No message was found for key '%s' and locale '%s'", key, locale.toLanguageTag()));
        }
        return key;
    }
}
