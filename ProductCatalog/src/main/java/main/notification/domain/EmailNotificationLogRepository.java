/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.notification.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import main.feedback.domain.FeedbackRequest;

import java.util.List;

/**
 *
 * @author zubbo
 */
public interface EmailNotificationLogRepository extends JpaRepository<EmailNotificationLog, Long> {
    List<EmailNotificationLog> findByFeedbackRequestOrderByCreatedAtDesc(FeedbackRequest feedbackRequest);
}
