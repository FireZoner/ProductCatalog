/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package feedback;

import org.springframework.data.jpa.repository.JpaRepository;
import user.AppUser;

import java.util.List;

/**
 *
 * @author zubbo
 */
public interface FeedbackRequestRepository extends JpaRepository<FeedbackRequest, Long> {
    List<FeedbackRequest> findByUserOrderByCreatedAtDesc(AppUser user);
    List<FeedbackRequest> findByStatusOrderByCreatedAtAsc(FeedbackStatus status);
}