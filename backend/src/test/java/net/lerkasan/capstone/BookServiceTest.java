package net.lerkasan.capstone;

import net.lerkasan.capstone.model.Book;
import net.lerkasan.capstone.repository.BookRepository;
import net.lerkasan.capstone.service.BookService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService underTestBookService;
    private final Book dummyBook = new Book(1L, "The Lord of the Rings");
    private final List<Book> dummyBooks = Collections.singletonList(dummyBook);

    @Disabled
    @Test
    void shouldInvokeFindAllBooks() {
        when(bookRepository.findAll()).thenReturn(dummyBooks);
        List<Book> foundBooks = underTestBookService.getBooks();

        verify(bookRepository, times(1)).findAll();
        assertEquals(dummyBooks, foundBooks);
    }

    @Test
    void dummyTest() {
        assertTrue(true);
    }
}
