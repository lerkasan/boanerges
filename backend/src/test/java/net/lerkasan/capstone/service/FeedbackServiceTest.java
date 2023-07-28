package net.lerkasan.capstone.service;

import net.lerkasan.capstone.repository.AnswerRepository;
import net.lerkasan.capstone.repository.FeedbackRepository;
import net.lerkasan.capstone.service.AnswerService;
import net.lerkasan.capstone.service.FeedbackService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class FeedbackServiceTest {

    @InjectMocks
    FeedbackService feedbackServiceUnderTest;

    @Mock
    FeedbackRepository feedbackRepo;

    @BeforeAll
    static void beforeAll() {
    }

    @Test
    void shouldThrowNullPointerExceptionWhenCreatingNullFeedback() {
        //then
        Assertions.assertThrows(NullPointerException.class, () -> {
            feedbackServiceUnderTest.create(null);
        });
    }
}