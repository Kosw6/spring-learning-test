package cholog;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class QueryingDAO {
    private JdbcTemplate jdbcTemplate;

    private RowMapper<Customer> customerRowMapper = (resultSet,rowNum)->{//반환받을 타입을 제네릭으로 설정하고 람다식으로 반환해줌
        Customer customer = new Customer(
                resultSet.getLong("id"),
                resultSet.getString("first_name"),
                resultSet.getString("last_name")
        );
        return customer;
    };

    public QueryingDAO(JdbcTemplate jdbcTemplate) {//스프링은 컴포넌트 스캔의 대상이 생성자가 하나일때 이걸 기준으로 빈을 등록함
        this.jdbcTemplate = jdbcTemplate;
    }

    /*
    private final RowMapper<Customer> actorRowMapper = (resultSet, rowNum) -> {
        Customer customer = new Customer(
                resultSet.getLong("id"),
                resultSet.getString("first_name"),
                resultSet.getString("last_name")
        );
        return customer;
    };
    추후 rowMapper에 대해 학습해보고 이용해보기
    */

    /**
     * public <T> T queryForObject(String sql, Class<T> requiredType)
     */
    public int count() {
        //TODO : customers 디비에 포함되어있는 row가 몇개인지 확인하는 기능 구현
        Integer countNum = jdbcTemplate.queryForObject("select count(*) from customers", Integer.class);
        return countNum;
    }

    /**
     * public <T> T queryForObject(String sql, Class<T> requiredType, @Nullable Object... args)
     */
    public String getLastName(Long id) {
        //TODO : 주어진 Id에 해당하는 customers의 lastName을 반환
        return jdbcTemplate.queryForObject("select last_name from customers where id=?",String.class,id);

    }

    /**
     * public <T> T queryForObject(String sql, RowMapper<T> rowMapper, @Nullable Object... args)
     */
    public Customer findCustomerById(Long id) {
        String sql = "select id, first_name, last_name from customers where id = ?";
        //TODO : 주어진 Id에 해당하는 customer를 객체로 반환

        return jdbcTemplate.queryForObject("select * from customers where id = ?",customerRowMapper,id);
    }

    /**
     * public <T> List<T> query(String sql, RowMapper<T> rowMapper)
     */
    public List<Customer> findAllCustomers() {
        String sql = "select id, first_name, last_name from customers";
        //TODO : 저장된 모든 Customers를 list형태로 반환
        List<Customer> query = jdbcTemplate.query(sql, customerRowMapper);
        return query;
    }

    /**
     * public <T> List<T> query(String sql, RowMapper<T> rowMapper, @Nullable Object... args)
     */
    public List<Customer> findCustomerByFirstName(String firstName) {
        String sql = "select id, first_name, last_name from customers where first_name = ?";
        //TODO : firstName을 기준으로 customer를 list형태로 반환
        List<Customer> query = jdbcTemplate.query(sql, customerRowMapper,firstName);
        return query;
    }
}
