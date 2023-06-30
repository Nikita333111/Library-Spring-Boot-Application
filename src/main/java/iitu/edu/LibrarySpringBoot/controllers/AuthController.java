package iitu.edu.LibrarySpringBoot.controllers;

import iitu.edu.LibrarySpringBoot.models.Person;
import iitu.edu.LibrarySpringBoot.services.PeopleService;
import iitu.edu.LibrarySpringBoot.util.PersonValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final PeopleService peopleService;
    private final PersonValidator personValidator;

    @Autowired
    public AuthController(PeopleService peopleService, PersonValidator personValidator) {
        this.peopleService = peopleService;
        this.personValidator = personValidator;
    }

    @GetMapping("/login")
    public String loginPage(){
        return "auth/login";
    }

    @GetMapping("/registration")
    public String registrationPage(@ModelAttribute("person") Person person){
        return "auth/registration";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute("person") @Valid Person person,
                               BindingResult bindingResult){
        //проверка на существующий email
        personValidator.validate(person,bindingResult);

        if (bindingResult.hasErrors())
            return "/auth/registration";

        //сохранение если не существует
        peopleService.save(person);
        return "redirect:/auth/login";
    }
}
