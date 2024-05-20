package ru.gb_spring.books.services;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import ru.gb_spring.books.models.Book;
import ru.gb_spring.books.repositories.BookRepository;

/**
 * The type Book service.
 */
@Service
@AllArgsConstructor
public class BookService {
    private BookRepository bookRepository;

    /**
     * Gets all books.
     *
     * @return the all books
     */
    public List<Book> getAllBooks() {return bookRepository.findAll();}

    /**
     * Gets book by id.
     *
     * @param id the id
     * @return the book by id
     */
    public Book getBookById(Long id) {return bookRepository.findById(id).get();}

    /**
     * Add book book.
     *
     * @param book the book
     * @return the book
     */
    public Book addBook(Book book) {return bookRepository.save(book);}

    /**
     * Update book book.
     *
     * @param book the book
     * @param id   the id
     * @return the book
     */
    public Book updateBook(Book book, Long id) {
        Book fBook = bookRepository.findById(id).get();
        fBook.setAuthor(book.getAuthor());
        fBook.setTitle(book.getTitle());
        fBook.setDescription(book.getDescription());
        bookRepository.save(fBook);
        return fBook;
    }

    /**
     * Remove book book.
     *
     * @param id the id
     * @return the book
     */
    public Book removeBook (Long id) {
        Book book = bookRepository.findById(id).get();
        bookRepository.deleteById(id);
        return book;
    }
}
