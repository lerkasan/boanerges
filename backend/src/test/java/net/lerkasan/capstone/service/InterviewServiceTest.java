package net.lerkasan.capstone.service;


import net.lerkasan.capstone.repository.InterviewRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class InterviewServiceTest {

    @InjectMocks
    InterviewService interviewServiceUnderTest;

    @Mock
    InterviewRepository interviewRepo;

    @BeforeAll
    static void beforeAll() {
    }

    @Test
    void shouldThrowNullPointerExceptionWhenCreatingNullInterview() {
        //then
        Assertions.assertThrows(NullPointerException.class, () -> {
            interviewServiceUnderTest.create(null);
        });
    }

    @Test
    void shouldThrowNullPointerExceptionWhenUpdatingNullInterview() {
        //then
        Assertions.assertThrows(NullPointerException.class, () -> {
            interviewServiceUnderTest.update(null);
        });
    }


    @ParameterizedTest
    @ValueSource(ints = { 0, -250 })
    void shouldThrowIllegalArgExceptionWhenGettingInterviewByInvalidQuestionId(long questionId) {
        //then
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            interviewServiceUnderTest.findByUserId(questionId);
        });
    }

    @ParameterizedTest
    @ValueSource(ints = { 0, -250 })
    void shouldThrowIllegalArgExceptionWhenDeletingInterviewByInvalidQuestionId(long questionId) {
        //then
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            interviewServiceUnderTest.delete(questionId);
        });
    }
}
