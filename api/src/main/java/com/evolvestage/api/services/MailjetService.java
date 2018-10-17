package com.evolvestage.api.services;

import com.evolvestage.api.properties.MailjetProperties;
import com.evolvestage.api.utils.MessagesService;
import com.mailjet.client.MailjetClient;
import com.mailjet.client.MailjetRequest;
import com.mailjet.client.resource.Email;
import lombok.AllArgsConstructor;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.stereotype.Service;

@Service
@CommonsLog
@AllArgsConstructor
public class MailjetService {
    private final MailjetClient client;
    private final MailjetProperties properties;
    private final MessagesService messagesService;

    public void sendEmail(String to, String subject, String text) {
        try {
            MailjetRequest request = new MailjetRequest(Email.resource)
                    .property(Email.FROMEMAIL, properties.getSenderEmail())
                    .property(Email.FROMNAME, properties.getSenderName())
                    .property(Email.SUBJECT, subject)
                    .property(Email.TEXTPART, text)
                    .property(Email.TO, to);
            client.post(request);
        } catch (Exception e) {
            log.error(e.getStackTrace());
            throw new RuntimeException(messagesService.getMessage("mail.fail.message"));
        }

    }
}
