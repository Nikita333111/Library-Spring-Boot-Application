package iitu.edu.LibrarySpringBoot.services;

import iitu.edu.LibrarySpringBoot.models.Book;
import iitu.edu.LibrarySpringBoot.models.Person;
import iitu.edu.LibrarySpringBoot.repositories.BooksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BooksService {

    private final BooksRepository booksRepository;

    @Autowired
    public BooksService(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }

    public List<Book> findAll(int page, int booksPerPage,boolean sortByYear){
        if(sortByYear)
            return booksRepository.findAll(PageRequest.of(page,booksPerPage,Sort.by("releaseYear"))).getContent();
        else
            return booksRepository.findAll(PageRequest.of(page,booksPerPage)).getContent();
    }
    public List<Book> findAll(boolean sortByYear){
        if (sortByYear)
            return booksRepository.findAll(Sort.by("releaseYear"));
        else return booksRepository.findAll();
    }

    public Book findOne(int id){
        Optional<Book> book = booksRepository.findById(id);

        return book.orElse(null);
    }

    @Transactional
    public void save(Book book){
        booksRepository.save(book);
    }

    @Transactional
    public void update(int id, Book updatedBook){
        Book bookToBeUpdated = booksRepository.findById(id).get();

        updatedBook.setId(id);
        updatedBook.setPerson(bookToBeUpdated.getPerson());
        booksRepository.save(updatedBook);
    }

    @Transactional
    public void delete(int id){
        booksRepository.deleteById(id);
    }

    @Transactional
    public void setPersonBook(Person person, int bookId){
        booksRepository.findById(bookId).ifPresent(book -> {
            book.setPerson(person);
            book.setDateOfTaking(new Date());
        });
    }

    @Transactional
    public void releaseBook(int id){
        booksRepository.findById(id).ifPresent(book -> {
            book.setPerson(null);
            book.setDateOfTaking(null);
        });
    }

    public List<Book> findBookStartsWith(String name){
        return booksRepository.findBooksByTitleStartsWith(name);
    }
}
