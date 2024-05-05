package projectspring.library.service;

import projectspring.library.model.Category;

import java.util.List;

public interface ICategoryService {
    List<Category> findAll();
    List<Category> findByActivated();
    Category save(Category category);
    Category findById(Long id);
    Category update(Category category);
    void enable(Long id);
    void delete(Long id);
}
