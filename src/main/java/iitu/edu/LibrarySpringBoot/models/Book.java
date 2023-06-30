package iitu.edu.LibrarySpringBoot.models;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Book")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Book {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "title")
    private String title;
    @Column(name = "author")
    private String author;
    @Column(name = "release_year")
    private int releaseYear;
    @Column(name = "date_of_taking")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateOfTaking;
    @Transient
    private boolean overdue = false;


    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private Person person;

    public Book(String title, String author, int releaseYear, Person person) {
        this.title = title;
        this.author = author;
        this.releaseYear = releaseYear;
        this.person = person;
    }
}
