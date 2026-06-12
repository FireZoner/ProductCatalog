package main.feedback.domain;

import main.user.domain.AppUser;
import main.user.domain.AppUserRepository;
import main.user.domain.UserRole;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author zubbo
 */
@DataJpaTest
@ActiveProfiles("test")
class FeedbackRequestRepositoryTest {

    @Autowired
    private FeedbackRequestRepository feedbackRequestRepository;

    @Autowired
    private AppUserRepository appUserRepository;

    @Test
    void shouldFindFeedbackRequestsByUser() {
        AppUser user = new AppUser(
                "stepan@example.com",
                "password-hash",
                "Степан",
                UserRole.USER
        );

        appUserRepository.save(user);

        FeedbackRequest request = new FeedbackRequest(
                user,
                null,
                "Степан",
                "+79999999999",
                "stepan@example.com",
                "Хочу уточнить детали товара"
        );

        feedbackRequestRepository.save(request);

        List<FeedbackRequest> requests =
                feedbackRequestRepository.findByUserOrderByCreatedAtDesc(user);

        assertEquals(1, requests.size());
        assertEquals("Степан", requests.getFirst().getContactName());
    }

    @Test
    void shouldFindFeedbackRequestsByStatus() {
        AppUser user = new AppUser(
                "ivan@example.com",
                "password-hash",
                "Иван",
                UserRole.USER
        );

        appUserRepository.save(user);

        FeedbackRequest request = new FeedbackRequest(
                user,
                null,
                "Иван",
                "+79999999999",
                "ivan@example.com",
                "Хочу уточнить детали товара"
        );

        feedbackRequestRepository.save(request);

        List<FeedbackRequest> requests =
                feedbackRequestRepository.findByStatusOrderByCreatedAtAsc(FeedbackStatus.PROCESSING);

        assertEquals(1, requests.size());
        assertEquals(FeedbackStatus.PROCESSING, requests.getFirst().getStatus());
    }
}