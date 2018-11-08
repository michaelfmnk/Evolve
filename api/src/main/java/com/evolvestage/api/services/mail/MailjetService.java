package com.evolvestage.api.services.mail;

import com.evolvestage.api.properties.MailjetProperties;
import com.evolvestage.api.services.mail.types.Email;
import com.evolvestage.api.utils.MessagesService;
import com.mailjet.client.MailjetClient;
import com.mailjet.client.MailjetRequest;
import lombok.AllArgsConstructor;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static com.mailjet.client.resource.Email.*;

@Service
@CommonsLog
@AllArgsConstructor
public class MailjetService implements EmailService {
    private final MailjetClient client;
    private final MailjetProperties properties;
    private final MessagesService messagesService;

    @Override
    public void sendEmail(Email email) {
        try {
            MailjetRequest request = new MailjetRequest(resource)
                    .property(FROMEMAIL, properties.getSenderEmail())
                    .property(FROMNAME, properties.getSenderName())
                    .property(MJTEMPLATEID, email.getEmailId())
                    .property(MJTEMPLATELANGUAGE, Objects.isNull(email.getLang()) ? true : email.getLang())
                    .property(TO, email.getTo())
                    .property(VARS, email.getVars());
            client.post(request);
        } catch (Exception e) {
            log.error(e.getStackTrace());
            throw new RuntimeException(messagesService.getMessage("mail.fail.message"));
        }
    }
}
