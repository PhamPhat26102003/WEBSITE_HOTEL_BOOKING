package projectspring.library.service;

import projectspring.library.model.Hotel;

import java.util.List;

public interface IHotelService {
    List<Hotel> findAll(String keyword);
    Hotel findById(Long id);
    Hotel save(Hotel hotel);
    Hotel update(Hotel hotel);
    void enable(Long id);
    void delete(Long id);
}
