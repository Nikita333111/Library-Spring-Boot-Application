package iitu.edu.LibrarySpringBoot.util;

import iitu.edu.LibrarySpringBoot.models.Person;
import iitu.edu.LibrarySpringBoot.services.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Objects;

@Component
public class PersonValidator implements Validator {
    private final PeopleService peopleService;

    @Autowired
    public PersonValidator(PeopleService peopleService) {
        this.peopleService = peopleService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(Person.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;

        if (peopleService.emailExists(person.getEmail()))
            errors.rejectValue("email","","Such email already registered");
    }

    public void validateUpdate(Object target, Errors errors,int id){
        Person person = (Person) target;

        if(Objects.equals(person.getEmail(), peopleService.findOne(id).getEmail()))
            return;

        if (peopleService.emailExists(person.getEmail()))
            errors.rejectValue("email","","Such email already registered");

    }
}
