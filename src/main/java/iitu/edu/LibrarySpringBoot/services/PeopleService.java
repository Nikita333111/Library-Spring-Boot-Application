package iitu.edu.LibrarySpringBoot.services;

import iitu.edu.LibrarySpringBoot.models.Person;
import iitu.edu.LibrarySpringBoot.repositories.PeopleRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@Transactional(readOnly = true)
public class PeopleService {

    private final PeopleRepository peopleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public PeopleService(PeopleRepository peopleRepository, PasswordEncoder passwordEncoder) {
        this.peopleRepository = peopleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<Person> findAll(){
        return peopleRepository.findAll();
    }

    public Person findOne(int id){
        Optional<Person> person = peopleRepository.findById(id);
        Hibernate.initialize(person.get().getBooks());
        person.get().getBooks().forEach(book -> {
            long diffInMillis = Math.abs(new Date().getTime() - book.getDateOfTaking().getTime());
            long diff = TimeUnit.DAYS.convert(diffInMillis,TimeUnit.MILLISECONDS);
            System.out.println(diff);
            if(diff >= 10)
                book.setOverdue(true);
        });
        return person.orElse(null);
    }

    public boolean emailExists(String email){
       return peopleRepository.findByEmail(email).isPresent();
    }

    @Transactional
    public void save(Person person){
        person.setRole("ROLE_USER");
        person.setPassword(passwordEncoder.encode(person.getPassword()));
        peopleRepository.save(person);
    }

    @Transactional
    public void update(int id, Person updatedPerson){
        updatedPerson.setId(id);
        updatedPerson.setPassword(passwordEncoder.encode(updatedPerson.getPassword()));
        peopleRepository.save(updatedPerson);
    }

    @Transactional
    public void delete(int id){
        peopleRepository.findById(id).ifPresent(person -> {
            person.getBooks().forEach(book -> book.setDateOfTaking(null));
        });
        peopleRepository.deleteById(id);
    }
}
