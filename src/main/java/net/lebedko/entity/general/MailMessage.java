package net.lebedko.entity.general;

import net.lebedko.entity.Validatable;

/**
 * alexandr.lebedko : 24.05.2017.
 */
public class MailMessage implements Validatable {

    private EmailAddress from;
    private EmailAddress to;
    private Text subject;
    private Text text;


    public MailMessage(EmailAddress from, EmailAddress to, Text subject, Text text) {
        this.from = from;
        this.to = to;
        this.subject = subject;
        this.text = text;
    }

    public EmailAddress getFrom() {
        return from;
    }

    public EmailAddress getTo() {
        return to;
    }

    public Text getSubject() {
        return subject;
    }

    public Text getText() {
        return text;
    }


    @Override
    public boolean isValid() {
        return to.isValid()
                && from.isValid()
                && subject.isValid()
                && text.isValid();
    }

    @Override
    public String toString() {
        return "MailMessage{" +
                "from=" + from +
                ", to=" + to +
                ", subject=" + subject +
                ", text=" + text +
                '}';
    }
}
