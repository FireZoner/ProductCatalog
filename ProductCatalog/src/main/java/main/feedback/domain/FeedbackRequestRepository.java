/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.feedback.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import main.user.domain.AppUser;

import java.util.List;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author zubbo
 */
public interface FeedbackRequestRepository extends JpaRepository<FeedbackRequest, Long> {
    List<FeedbackRequest> findByUserOrderByCreatedAtDesc(AppUser user);
    List<FeedbackRequest> findByStatusOrderByCreatedAtAsc(FeedbackStatus status);
    
    @Query("""
            select request
            from FeedbackRequest request
            left join fetch request.product
            where request.user = :user
            order by request.createdAt desc
            """)
    List<FeedbackRequest> findHistoryByUser(AppUser user);
}