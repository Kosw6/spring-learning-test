package cholog;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "author")//읽기전용, db에 Author테이블에 컬럼이 생성되지 않음 원래는 없어도 되지만 연관관계를 위해서 필드를 지정해뒀으므로
    //mappedBy="반대 엔티티에 지정된 필드이름"을 지정해 주어야 한다.
    private Person person;

    @OneToMany(mappedBy = "author")
    private Set<BookAuthor> books = new HashSet<>();
    public Author(Person person) {
    }

    public Author() {
    }

    public Long getId() {
        return id;
    }

    public Person getPerson() {
        return this.person;
    }

    public Author(Set<BookAuthor> books) {
        this.books = books;
    }

    void setPerson(Person person){
        this.person = person;
    }

}