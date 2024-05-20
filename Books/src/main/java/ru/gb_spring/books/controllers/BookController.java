package ru.gb_spring.books.controllers;

import java.util.List;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Metrics;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import ru.gb_spring.books.services.BookService;
import ru.gb_spring.books.models.Book;

/**
 * Контроллер Книга.
 */
@RestController
@RequestMapping("/books")
@AllArgsConstructor
public class BookController {

    private BookService bookService;

    /**
     * Метрика количества запросов
     */
    private final Counter requestCounter = Metrics.counter("request_count_total");

    /**
     * Получает книгу.
     *
     * @return книгу
     */
    @GetMapping
    public ResponseEntity<List<Book>> getAllBook() {
        requestCounter.increment();
        return new ResponseEntity<>(bookService.getAllBooks(), HttpStatus.OK);
    }

    /**
     * Получает книгу по идентификатору.
     *
     * @param id идентификатор
     * @return книгу с указанным id
     */
    @GetMapping("/{id}")
    public ResponseEntity<Book> getBook(@PathVariable Long id) {
        Book book;
        try {
            book = bookService.getBookById(id);
            requestCounter.increment();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return new ResponseEntity<>(book, HttpStatus.OK);
    }

    /**
     * Добавить книгу.
     *
     * @param book the book
     * @return статус
     */
    @PostMapping
    public ResponseEntity<Book> addBook(@RequestBody Book book) {
        Book newBook = bookService.addBook(book);
        requestCounter.increment();
        return new ResponseEntity<Book>(newBook, HttpStatus.CREATED);
    }

    /**
     * Обновить книгу.
     *
     * @param id   идентификатор
     * @param book книга
     * @return статус
     */
    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book book) {
        Book updBook;
        try {
            updBook = bookService.updateBook(book, id);
            requestCounter.increment();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return new ResponseEntity<>(updBook, HttpStatus.OK);
    }

    /**
     * Удалить книгу.
     *
     * @param id идентификатор
     * @return статус
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Book> deleteBook (@PathVariable Long id) {
        Book delBook;
        try {
            delBook = bookService.removeBook(id);
            requestCounter.increment();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return new ResponseEntity<>(delBook, HttpStatus.OK);
    }
}
