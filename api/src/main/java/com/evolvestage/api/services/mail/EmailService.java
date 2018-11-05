package com.evolvestage.api.services.mail;

import com.evolvestage.api.services.mail.types.Email;

public interface EmailService {
    void sendEmail(Email email);
}
