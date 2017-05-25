package net.lebedko.service.mail;

import net.lebedko.entity.Validatable;
import net.lebedko.entity.general.EmailAddress;
import net.lebedko.entity.general.Text;

/**
 * alexandr.lebedko : 24.05.2017.
 */
public class MailMessage implements Validatable {

    private EmailAddress from;
    private EmailAddress to;
    private Text subject;
    private Text text;


    public MailMessage() {
        this.from = from;
        this.to = to;
        this.subject = subject;
        this.text = text;
    }

    public void setFrom(EmailAddress from) {
        this.from = from;
    }

    public void setTo(EmailAddress to) {
        this.to = to;
    }

    public void setSubject(Text subject) {
        this.subject = subject;
    }

    public void setText(Text text) {
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
