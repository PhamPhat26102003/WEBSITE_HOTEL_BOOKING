package projectspring.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import projectspring.library.model.BookItem;

public interface IBookItemRepository extends JpaRepository<BookItem, Long> {
}
