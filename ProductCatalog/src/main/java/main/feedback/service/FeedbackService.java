/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.feedback.service;

import main.feedback.domain.FeedbackRequest;
import main.feedback.domain.FeedbackRequestRepository;
import main.feedback.web.CreateFeedbackRequest;
import main.notification.domain.EmailNotificationLog;
import main.notification.domain.EmailNotificationLogRepository;
import main.notification.service.EmailSender;
import main.product.domain.Product;
import main.product.domain.ProductRepository;
import main.user.domain.AppUser;
import main.user.domain.AppUserRepository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

/**
 *
 * @author zubbo
 */
@Service
public class FeedbackService {

    private final FeedbackRequestRepository feedbackRequestRepository;
    private final EmailNotificationLogRepository emailNotificationLogRepository;
    private final ProductRepository productRepository;
    private final AppUserRepository appUserRepository;
    private final EmailSender emailSender;
    private final String adminEmail;

    public FeedbackService(
            FeedbackRequestRepository feedbackRequestRepository,
            EmailNotificationLogRepository emailNotificationLogRepository,
            ProductRepository productRepository,
            AppUserRepository appUserRepository,
            EmailSender emailSender,
            @Value("${app.feedback.admin-email}") String adminEmail
    ) {
        this.feedbackRequestRepository = feedbackRequestRepository;
        this.emailNotificationLogRepository = emailNotificationLogRepository;
        this.productRepository = productRepository;
        this.appUserRepository = appUserRepository;
        this.emailSender = emailSender;
        this.adminEmail = adminEmail;
    }

    @Transactional
    public FeedbackRequest createFeedback(CreateFeedbackRequest request, String userEmail) {
        AppUser user = appUserRepository.findByEmail(userEmail)
                .orElse(null);

        Product product = null;
        if (request.getProductId() != null) {
            product = productRepository.findById(request.getProductId())
                    .orElse(null);
        }

        FeedbackRequest feedbackRequest = new FeedbackRequest(
                user,
                product,
                request.getContactName(),
                request.getContactPhone(),
                request.getContactEmail(),
                request.getMessageText()
        );

        feedbackRequestRepository.save(feedbackRequest);

        String subject = buildSubject(feedbackRequest);
        String body = buildBody(feedbackRequest);

        try {
            emailSender.send(adminEmail, subject, body);

            feedbackRequest.markAsSent();

            emailNotificationLogRepository.save(
                    EmailNotificationLog.success(feedbackRequest, adminEmail, subject)
            );
        } catch (Exception exception) {
            feedbackRequest.markAsError(exception.getMessage());

            emailNotificationLogRepository.save(
                    EmailNotificationLog.error(
                            feedbackRequest,
                            adminEmail,
                            subject,
                            exception.getMessage()
                    )
            );
        }

        return feedbackRequestRepository.save(feedbackRequest);
    }

    private String buildSubject(FeedbackRequest feedbackRequest) {
        if (feedbackRequest.getProduct() == null) {
            return "Новое обращение пользователя";
        }

        return "Новое обращение по товару: " + feedbackRequest.getProduct().getTitle();
    }

    private String buildBody(FeedbackRequest feedbackRequest) {
        StringBuilder body = new StringBuilder();

        body.append("Поступило новое обращение.\n\n");

        if (feedbackRequest.getProduct() != null) {
            body.append("Товар: ")
                    .append(feedbackRequest.getProduct().getTitle())
                    .append("\n\n");
        }

        body.append("Имя: ")
                .append(feedbackRequest.getContactName())
                .append("\n");

        body.append("Телефон: ")
                .append(feedbackRequest.getContactPhone())
                .append("\n");

        body.append("Email: ")
                .append(feedbackRequest.getContactEmail())
                .append("\n\n");

        body.append("Сообщение:\n")
                .append(feedbackRequest.getMessageText());

        return body.toString();
    }
    
    @Transactional(readOnly = true)
    public List<FeedbackRequest> findCurrentUserFeedbackRequests(String userEmail) {
        AppUser user = appUserRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("Пользователь не найден"));

        return feedbackRequestRepository.findHistoryByUser(user);
    }
}
