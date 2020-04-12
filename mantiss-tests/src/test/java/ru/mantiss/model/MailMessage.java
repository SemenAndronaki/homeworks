package ru.mantiss.model;

import org.subethamail.wiser.WiserMessage;

public class MailMessage {
    public String to;
    public String text;

    public MailMessage(String to, String text) {
        this.to = to;
        this.text = text;
    }

    private MailMessage toModelMail(WiserMessage m) {
        return null;
    }
}
