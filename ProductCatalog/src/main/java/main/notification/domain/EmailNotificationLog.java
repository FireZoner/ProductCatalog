/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.notification.domain;

import jakarta.persistence.*;
import main.feedback.domain.FeedbackRequest;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 *
 * @author zubbo
 */
@Entity
@Table(name = "email_notification_logs")
public class EmailNotificationLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Лог относится к конкретному обращению.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "feedback_request_id", nullable = false)
    private FeedbackRequest feedbackRequest;

    @Column(name = "recipient_email", nullable = false, length = 255)
    private String recipientEmail;

    @Column(name = "subject", nullable = false, length = 255)
    private String subject;

    @Enumerated(EnumType.STRING)
    @Column(name = "delivery_status", nullable = false, length = 30)
    private EmailDeliveryStatus deliveryStatus;

    @Column(name = "error_message", columnDefinition = "TEXT")
    private String errorMessage;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    protected EmailNotificationLog() {
        // Конструктор нужен JPA
    }

    public EmailNotificationLog(
            FeedbackRequest feedbackRequest,
            String recipientEmail,
            String subject,
            EmailDeliveryStatus deliveryStatus,
            String errorMessage
    ) {
        this.feedbackRequest = feedbackRequest;
        this.recipientEmail = recipientEmail;
        this.subject = subject;
        this.deliveryStatus = deliveryStatus;
        this.errorMessage = errorMessage;
        this.createdAt = LocalDateTime.now();
    }

    public static EmailNotificationLog success(
            FeedbackRequest feedbackRequest,
            String recipientEmail,
            String subject
    ) {
        return new EmailNotificationLog(
                feedbackRequest,
                recipientEmail,
                subject,
                EmailDeliveryStatus.SUCCESS,
                null
        );
    }

    public static EmailNotificationLog error(
            FeedbackRequest feedbackRequest,
            String recipientEmail,
            String subject,
            String errorMessage
    ) {
        return new EmailNotificationLog(
                feedbackRequest,
                recipientEmail,
                subject,
                EmailDeliveryStatus.ERROR,
                errorMessage
        );
    }

    public Long getId() {
        return id;
    }

    public FeedbackRequest getFeedbackRequest() {
        return feedbackRequest;
    }

    public String getRecipientEmail() {
        return recipientEmail;
    }

    public String getSubject() {
        return subject;
    }

    public EmailDeliveryStatus getDeliveryStatus() {
        return deliveryStatus;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public boolean isSuccess() {
        return deliveryStatus == EmailDeliveryStatus.SUCCESS;
    }

    public boolean isError() {
        return deliveryStatus == EmailDeliveryStatus.ERROR;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EmailNotificationLog that)) return false;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
