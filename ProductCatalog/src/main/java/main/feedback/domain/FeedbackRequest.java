package main.feedback.domain;

import jakarta.persistence.*;
import main.product.domain.Product;
import main.user.domain.AppUser;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 *
 * @author zubbo
 */
@Entity
@Table(name = "feedback_requests")
public class FeedbackRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private AppUser user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "contact_name", nullable = false, length = 150)
    private String contactName;

    @Column(name = "contact_phone", nullable = false, length = 30)
    private String contactPhone;

    @Column(name = "contact_email", nullable = false, length = 255)
    private String contactEmail;

    @Column(name = "message_text", nullable = false, columnDefinition = "TEXT")
    private String messageText;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 30)
    private FeedbackStatus status;

    @Column(name = "error_message", columnDefinition = "TEXT")
    private String errorMessage;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "processed_at")
    private LocalDateTime processedAt;

    protected FeedbackRequest() {
    }

    public FeedbackRequest(AppUser user, Product product, String contactName, String contactPhone,
            String contactEmail, String messageText) {
        this.user = user;
        this.product = product;
        this.contactName = contactName;
        this.contactPhone = contactPhone;
        this.contactEmail = contactEmail;
        this.messageText = messageText;
        this.status = FeedbackStatus.PROCESSING;
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public AppUser getUser() {
        return user;
    }

    public Product getProduct() {
        return product;
    }

    public String getContactName() {
        return contactName;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public String getMessageText() {
        return messageText;
    }

    public FeedbackStatus getStatus() {
        return status;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getProcessedAt() {
        return processedAt;
    }

    public void markAsSent() {
        this.status = FeedbackStatus.SENT;
        this.errorMessage = null;
        this.processedAt = LocalDateTime.now();
    }

    public void markAsError(String errorMessage) {
        this.status = FeedbackStatus.ERROR;
        this.errorMessage = errorMessage;
        this.processedAt = LocalDateTime.now();
    }

    public boolean isProcessing() {
        return status == FeedbackStatus.PROCESSING;
    }

    public boolean isSent() {
        return status == FeedbackStatus.SENT;
    }

    public boolean isError() {
        return status == FeedbackStatus.ERROR;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FeedbackRequest that)) return false;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}