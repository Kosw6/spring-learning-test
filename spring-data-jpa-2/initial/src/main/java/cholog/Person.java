package cholog;

import jakarta.persistence.*;

@Entity
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @OneToOne
    @JoinColumn(name = "author_id")//@JoinColumn으로 외래키를 지정해준다.
    private Author author;

    public Person() {}//jpa는 기본 생성자 필요

    public Person(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Author getAuthor() {
        return this.author;
    }

    public void setAuthor(Author author){
        this.author = author;
        author.setPerson(this);
    }
}
