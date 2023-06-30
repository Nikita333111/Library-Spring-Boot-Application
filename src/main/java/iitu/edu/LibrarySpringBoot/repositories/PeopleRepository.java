package iitu.edu.LibrarySpringBoot.repositories;

import iitu.edu.LibrarySpringBoot.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PeopleRepository extends JpaRepository<Person, Integer> {
    @Query("SELECT p FROM Person p JOIN FETCH p.books WHERE p.id = (:id)")
    public Person findByIdAndFetchRolesEagerly(@Param("id") int id);

    Optional<Person> findByEmail(String email);
}
