package main.notification.service;

/**
 *
 * @author zubbo
 */
public interface EmailSender {
    void send(String recipientEmail, String subject, String body);
}
