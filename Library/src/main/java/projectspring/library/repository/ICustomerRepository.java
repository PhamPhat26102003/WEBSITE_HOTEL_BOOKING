package projectspring.library.repository;

import org.springframework.beans.factory.annotation.CustomAutowireConfigurer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import projectspring.library.model.Customer;

@Repository
public interface ICustomerRepository extends JpaRepository<Customer, Long> {
    Customer findByUsername(String username);
}
