package cholog;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class OneToOneTest {
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private PersonRepository personRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    void uniDirection() {
        Person person = new Person("사람");
        entityManager.persist(person);
        Author author = new Author(person);
        entityManager.persist(author);
        person.setAuthor(author);
        entityManager.flush();//db에 커밋
        entityManager.clear();

        Author persistAuthor = entityManager.find(Author.class, author.getId());
        assertThat(persistAuthor).isNotNull();
        assertThat(persistAuthor.getPerson()).isNotNull();
    }

    @Test
    void biDirection() {
        Person person = new Person("사람");
        entityManager.persist(person);

        Author author = new Author(person);
        entityManager.persist(author);
        person.setAuthor(author);
        entityManager.flush();
        entityManager.clear();

        Person persistPerson = entityManager.find(Person.class, person.getId());
        assertThat(persistPerson).isNotNull();
        assertThat(persistPerson.getAuthor()).isNotNull();
    }

    @Test
    void findByIdForAuthor() {
        Person person = new Person("사람");
        entityManager.persist(person);

        Author author = new Author(person);
        entityManager.persist(author);

        person.setAuthor(author);
        entityManager.flush();
        entityManager.clear();

        /*author은 읽기전용매핑으로 설정했고 실제로 db테이블에는 person과 관련된 fk필드는 없지만
        해당 엔티티를 조회하면 가져올 수 있다.
        기본적으로 jpa는 프록시를 활용하여 지연로딩과 즉시로딩을 사용하는데
        xxxToOne으로 된 필드들은 기본세팅이 즉시로딩이다.
        즉시로딩은 엔티티를 가져오면 연관된 연관관계 엔티티또한 가져오는 것이다.
        xxxToMany로 된 애들은 기본세팅이 지연로딩인데
        지연로딩은 아직 사용하지 않은 연관관계 필드의 엔티티는 프록시로 두고 db에서 가져오지 않는 방식이다.
        왠만해서는 지연로딩으로 하는 것이 좋다 왜냐면 N+1문제 발생 가능성을 줄일 수 있기 때문이다.
        N+1문제는 쿼리 하나를 보냈을때 N개의 쿼리가 딸려 나가는 것을 의미하는데 여기서는 N개의 연관관계를 의미한다.
         */
        Optional<Author> persistAuthor = authorRepository.findById(author.getId());
        assertThat(persistAuthor).isPresent();
        assertThat(persistAuthor.get().getPerson()).isNotNull();
        assertThat(persistAuthor.get().getPerson().getName()).isEqualTo("사람");//즉시로딩이므로 따로 셀렉쿼리 안나감
    }

    @Test
    void findByIdForPerson() {
        Person person = new Person("사람");
        entityManager.persist(person);

        Author author = new Author(person);
        entityManager.persist(author);

        person.setAuthor(author);
        entityManager.flush();
        entityManager.clear();

        Optional<Person> persistPerson = personRepository.findById(person.getId());
        assertThat(persistPerson).isPresent();
        assertThat(persistPerson.get().getAuthor()).isNotNull();
    }
}
