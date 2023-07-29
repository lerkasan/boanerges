package net.lerkasan.capstone.service;

import net.lerkasan.capstone.exception.NotFoundException;
import net.lerkasan.capstone.repository.QuestionRepository;
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
class QuestionServiceTest {

    @InjectMocks
    QuestionService questionServiceUnderTest;

    @Mock
    QuestionRepository questionRepo;

    @BeforeAll
    static void beforeAll() {
    }

    @Test
    void shouldThrowNullPointerExceptionWhenCreatingNullQuestion() {
        //then
        Assertions.assertThrows(NullPointerException.class, () -> {
            questionServiceUnderTest.create(null);
        });
    }

    @Test
    void shouldThrowNotFoundExceptionWhenGettingQuestionByNonExistentIds() {
        //then
        Assertions.assertThrows(NotFoundException.class, () -> {
            questionServiceUnderTest.findByIdAndInterviewId(Long.MAX_VALUE, Long.MAX_VALUE);
        });
    }
}