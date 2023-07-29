package net.lerkasan.capstone.service;

import net.lerkasan.capstone.repository.AnswerRepository;
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
class AnswerServiceTest {

    @InjectMocks
    AnswerService answerServiceUnderTest;

    @Mock
    AnswerRepository answerRepo;

    @BeforeAll
    static void beforeAll() {
    }

    @Test
    void shouldThrowNullPointerExceptionWhenCreatingNullAnswer() {
        //then
        Assertions.assertThrows(NullPointerException.class, () -> {
            answerServiceUnderTest.create(null);
        });
    }


    @ParameterizedTest
    @ValueSource(ints = { 0, -12 })
    void shouldThrowIllegalArgExceptionWhenGettingAnswerByInvalidQuestionId(long questionId) {
        //then
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            answerServiceUnderTest.findByQuestionId(questionId);
        });
    }
}
