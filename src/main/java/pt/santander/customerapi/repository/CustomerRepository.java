package pt.santander.customerapi.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pt.santander.customerapi.entity.Customer;

import java.util.List;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Integer> {


    public List<Customer> findByActiveAndNameContaining(Boolean Active, String name);
    public List<Customer> findByActiveAndNifAndNameContaining(Boolean Active, String name, String nif);
    public List<Customer> findByActiveAndNif(Boolean Active, String nif);
    public List<Customer> findByActive(Boolean Active);
    public List<Customer> findAll();
    public List<Customer> findByNameContaining(String name);
    public List<Customer> findByEmail(String email);
    public List<Customer> findByNifBetween(String rangeMin, String rangeMax);

    @Query(value = "select * from customers where email like %?1%", nativeQuery = true) // value onde colocamos a query
    public List<Customer> findByEmailGroup(String group);
}