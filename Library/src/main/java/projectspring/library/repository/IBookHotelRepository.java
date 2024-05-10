package projectspring.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import projectspring.library.model.BookHotel;

@Repository
public interface IBookHotelRepository extends JpaRepository<BookHotel, Long> {
}
