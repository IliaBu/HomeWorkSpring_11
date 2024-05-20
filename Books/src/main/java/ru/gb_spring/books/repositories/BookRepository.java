package ru.gb_spring.books.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ru.gb_spring.books.models.Book;
import java.util.List;

/**
 * The interface Book repository.
 */
@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    /**
     * Find by author list.
     *
     * @param author the author
     * @return the list
     */
    public List<Book> findByAuthor(String author);

    /**
     * Find by title list.
     *
     * @param title the title
     * @return the list
     */
    public List<Book> findByTitle(String title);
}
