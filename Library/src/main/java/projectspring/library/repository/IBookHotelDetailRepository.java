package projectspring.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import projectspring.library.model.BookHotelDetail;

@Repository
public interface IBookHotelDetailRepository extends JpaRepository<BookHotelDetail, Long> {
}
