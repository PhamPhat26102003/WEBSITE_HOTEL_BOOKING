package projectspring.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import projectspring.library.model.Booking;

@Repository
public interface IBookingRepository extends JpaRepository<Booking, Long> {
}
