package iitu.edu.LibrarySpringBoot.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.context.annotation.Role;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.List;

@Entity
@Table(name = "Person")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Person {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotEmpty(message = "Name should not be empty")
    @Size(min = 5, max = 100, message = "Name should be between 5 and 100 characters")
    @Column(name = "full_name")
    private String fullName;
    @Min(value = 0, message = "age can not be negative")
    @Column(name = "age")
    private int age;

    @Email
    @Column(name = "email")
    @ToString.Exclude
    private String email;

    @ToString.Exclude
    @Column(name = "password")
    private String password;

    @ToString.Exclude
    @Pattern(regexp = "ROLE_USER|ROLE_ADMIN", message = "It should be of the form 'ROLE_USER' or 'ROLE_ADMIN'")
    @Column(name = "role")
    private String role;

    @OneToMany(mappedBy = "person",fetch = FetchType.LAZY)
    private List<Book> books;

    public Person(String fullName, int age, List<Book> books) {
        this.fullName = fullName;
        this.age = age;
        this.books = books;
    }
}
