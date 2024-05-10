package projectspring.library.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import projectspring.library.model.Category;
import projectspring.library.model.City;
import projectspring.library.model.Hotel;

import java.util.List;

@Repository
public interface IHotelRepository extends JpaRepository<Hotel, Long> {
    @Query("SELECT h FROM Hotel h WHERE CONCAT(h.name, h.address) LIKE %?1%")
    List<Hotel> findAll(String keyword);

    @Query("SELECT h FROM Hotel h WHERE h.is_activated=true AND h.is_deleted=false")
    List<Hotel> findByActivated();

    @Query("SELECT h FROM Hotel h")
    Page<Hotel> pageHotel(Pageable pageable);

    @Query("SELECT h FROM Hotel h WHERE h.city = :city")
    List<Hotel> findProductByCity(@Param("city")City city);

    @Query("SELECT h FROM Hotel h WHERE h.city = :city AND h.category = :category")
    List<Hotel> getRelatedHotel(@Param("city") City city, @Param("category") Category category);

    @Query("SELECT h FROM Hotel h WHERE h.category = :category")
    List<Hotel> findHotelByCategory(@Param("category")Category category);
}
