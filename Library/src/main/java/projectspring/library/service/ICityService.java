package projectspring.library.service;

import projectspring.library.model.City;

import java.util.List;

public interface ICityService {
    List<City> findAll();
    List<City> findByActivated();
    City findById(Long id);
    City save(City city);
    City update(City city);
    void enable(Long id);
    void delete(Long id);
}
