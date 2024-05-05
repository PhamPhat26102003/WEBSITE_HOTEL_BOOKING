package projectspring.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import projectspring.library.model.City;

import java.util.List;

@Repository
public interface ICityRepositoty extends JpaRepository<City, Long> {

    @Query("SELECT c FROM City c WHERE c.is_activated=true AND c.is_deleted=false")
    List<City> findByActivated();
}
