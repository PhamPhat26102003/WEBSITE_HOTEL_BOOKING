package projectspring.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import projectspring.library.model.Category;
import projectspring.library.model.Hotel;

import java.util.List;

@Repository
public interface ICategoryRepository extends JpaRepository<Category, Long> {
    @Query("SELECT c FROM Category c WHERE c.is_activated=true AND c.is_deleted=false")
    List<Category> findByActivated();
}
