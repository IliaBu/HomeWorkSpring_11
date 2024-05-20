package ru.gb_spring.books;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.gb_spring.books.models.Book;
import ru.gb_spring.books.repositories.BookRepository;
import ru.gb_spring.books.services.BookService;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

/**
 * Интеграционные тесты контролёра сайта книг
 */
@SpringBootTest
class BookServiceIntegrationTest {

	@Autowired
	public BookService bookService;
	@MockBean
	private BookRepository bookRepository;

	private Book book1;


	/**
	 * Задаём начальные настройки параметров
	 */
	@BeforeEach
	public void setup() {

		book1 = new Book();
		book1.setId(1L);
		book1.setTitle("Title1");
		book1.setAuthor("Author1");
		book1.setDescription("Description1");

	}

	/**
	 * Интеграционный тест запроса страницы с книгами
	 */
	@Test
	public void getAllBookTest() {

		Mockito.when(bookRepository.findAll()).thenReturn(Collections.singletonList(book1));

		List<Book> books = bookService.getAllBooks();

		// Проверка вызова метода findAll и корректности полученных данных
		verify(bookRepository).findAll();
		assertThat(books).isNotEmpty();
		assertThat(books).hasSize(1);
		assertThat(books.get(0).getId()).isEqualTo(book1.getId());

	}

}
