package com.filmdb.auth.auth_service.domain.model.valueobject;

import lombok.EqualsAndHashCode;
import lombok.ToString;

// TODO: Consider if this class should be a DTO instead :)
@EqualsAndHashCode
@ToString
public class EmailMessage {

    private Email to;
    private String subject;
    private String body;

    private EmailMessage(Email to, String subject, String body) {
        this.to = to;
        this.subject = subject;
        this.body = body;
    }

    public Email to() {
        return this.to;
    }

    public String subject() {
        return this.subject;
    }

    public String body() {
        return this.body;
    }

    public static EmailMessage of(Email to, String subject, String message) {
        return new EmailMessage(to, subject, message);
    }

}
