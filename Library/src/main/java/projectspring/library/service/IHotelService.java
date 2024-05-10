package projectspring.library.service;

import org.springframework.data.domain.Page;
import projectspring.library.model.Category;
import projectspring.library.model.City;
import projectspring.library.model.Hotel;

import java.util.List;

public interface IHotelService {
    List<Hotel> findAll(String keyword);
    List<Hotel> findByActivated();
    List<Hotel> findHotelByCity(City city);
    List<Hotel> findHotelByCategory(Category category);
    List<Hotel> getRelatedHotel(City city, Category category);
    Page<Hotel> pageHotel(int pageNo);
    Hotel findById(Long id);
    Hotel save(Hotel hotel);
    Hotel update(Hotel hotel);
    void enable(Long id);
    void delete(Long id);
}
