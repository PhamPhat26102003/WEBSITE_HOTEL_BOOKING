package projectspring.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import projectspring.library.model.Admin;
import projectspring.library.model.Hotel;

@Repository
public interface IAdminRepository extends JpaRepository<Admin, Long> {
    Admin findByUsername(String username);
}
