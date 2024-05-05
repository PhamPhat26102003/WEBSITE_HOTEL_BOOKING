package projectspring.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import projectspring.library.model.Hotel;

import java.util.List;

@Repository
public interface IHotelRepository extends JpaRepository<Hotel, Long> {
    @Query("SELECT h FROM Hotel h WHERE CONCAT(h.name, h.address) LIKE %?1%")
    List<Hotel> findAll(String keyword);
}
