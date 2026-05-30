package main.feedback.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author zubbo
 */
public class FeedbackRequestTest {

    @Test
    void shouldCreateFeedbackWithProcessingStatus() {
        FeedbackRequest request = new FeedbackRequest(
                null,
                null,
                "Степан",
                "+79999999999",
                "stepan@example.com",
                "Хочу уточнить детали товара"
        );

        assertEquals(FeedbackStatus.PROCESSING, request.getStatus());
        assertTrue(request.isProcessing());
    }

    @Test
    void shouldMarkFeedbackAsSent() {
        FeedbackRequest request = new FeedbackRequest(
                null,
                null,
                "Степан",
                "+79999999999",
                "stepan@example.com",
                "Хочу уточнить детали товара"
        );

        request.markAsSent();

        assertEquals(FeedbackStatus.SENT, request.getStatus());
        assertTrue(request.isSent());
        assertNull(request.getErrorMessage());
        assertNotNull(request.getProcessedAt());
    }

    @Test
    void shouldMarkFeedbackAsError() {
        FeedbackRequest request = new FeedbackRequest(
                null,
                null,
                "Степан",
                "+79999999999",
                "stepan@example.com",
                "Хочу уточнить детали товара"
        );

        request.markAsError("SMTP error");

        assertEquals(FeedbackStatus.ERROR, request.getStatus());
        assertTrue(request.isError());
        assertEquals("SMTP error", request.getErrorMessage());
        assertNotNull(request.getProcessedAt());
    }
}
