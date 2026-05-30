package main.notification.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 *
 * @author zubbo
 */
@Service
public class SmtpEmailSender implements EmailSender {

    private final JavaMailSender javaMailSender;

    public SmtpEmailSender(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public void send(String recipientEmail, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(recipientEmail);
        message.setSubject(subject);
        message.setText(body);

        javaMailSender.send(message);
    }
}
