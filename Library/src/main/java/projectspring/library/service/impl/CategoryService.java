package projectspring.library.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projectspring.library.model.Category;
import projectspring.library.repository.ICategoryRepository;
import projectspring.library.service.ICategoryService;

import java.util.List;

@Service
public class CategoryService implements ICategoryService {
    @Autowired
    private ICategoryRepository categoryRepository;
    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public List<Category> findByActivated() {
        return categoryRepository.findByActivated();
    }

    @Override
    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Category findById(Long id) {
        return categoryRepository.getById(id);
    }

    @Override
    public Category update(Category category) {
        Category categoryUpdate = categoryRepository.getById(category.getId());
            categoryUpdate.setName(category.getName());
            categoryUpdate.set_activated(category.is_activated());
            categoryUpdate.set_deleted(category.is_deleted());
        return categoryRepository.save(categoryUpdate);
    }

    @Override
    public void enable(Long id) {
        Category category = categoryRepository.getById(id);
        category.set_activated(true);
        category.set_deleted(false);
        categoryRepository.save(category);
    }

    @Override
    public void delete(Long id) {
        Category category = categoryRepository.getById(id);
        category.set_deleted(true);
        category.set_activated(false);
        categoryRepository.save(category);
    }
}
