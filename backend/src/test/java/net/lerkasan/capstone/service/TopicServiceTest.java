package net.lerkasan.capstone.service;

import net.lerkasan.capstone.exception.NotFoundException;
import net.lerkasan.capstone.repository.TopicRepository;
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
class TopicServiceTest {

    @InjectMocks
    TopicService topicServiceUnderTest;

    @Mock
    TopicRepository topicRepo;

    @BeforeAll
    static void beforeAll() {
    }

    @ParameterizedTest
    @ValueSource(ints = { 0, -250 })
    void shouldThrowIllegalArgExceptionWhenGettingTopicByInvalidId(long id) {
        //then
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            topicServiceUnderTest.findById(id);
        });
    }

    @Test
    void shouldThrowNotFoundExceptionWhenGettingTopicByNonExistentId() {
        //then
        Assertions.assertThrows(NotFoundException.class, () -> {
            topicServiceUnderTest.findById(Long.MAX_VALUE);
        });
    }
}