package cholog;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Publisher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @OneToMany(mappedBy = "publisher")
    private Set<Book> book = new HashSet<>();
    public Publisher(String name) {
        this.name = name;
    }

    public Publisher() {
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }


    public Set<Book> getBooks() {
        return this.book;
    }

    public void addBook(Book book){
        this.book.add(book);
//        book.setPublisher(this);
    }
}
