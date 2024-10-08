package cholog;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @ManyToOne
    @JoinColumn(name = "publisher")
    private Publisher publisher;

    @OneToMany(mappedBy = "book")
    private Set<BookAuthor> authors = new HashSet<>();

    public Book() {

    }

    public Book(String name, Publisher publisher) {
        this.name = name;
        this.publisher = publisher;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Publisher getPublisher() {
        return this.publisher;
    }

    public Set<BookAuthor> getAuthors() {
        return this.authors;
    }

//    public void setPublisher(Publisher publisher){
//        this.publisher = publisher;
//    }
}
