package iitu.edu.LibrarySpringBoot.repositories;

import iitu.edu.LibrarySpringBoot.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BooksRepository extends JpaRepository<Book, Integer> {
    public List<Book> findBooksByTitleStartsWith(String name);
}
