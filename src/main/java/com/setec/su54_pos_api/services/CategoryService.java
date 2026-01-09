package com.setec.su54_pos_api.services;

import java.util.List;

import com.setec.su54_pos_api.exceptions.MyResourceNotFoundException;
import com.setec.su54_pos_api.models.Category;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import com.setec.su54_pos_api.repositorys.CategoryRepository;

import jakarta.transaction.Transactional;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository _categoryRepository) {
        super();
        this.categoryRepository = _categoryRepository;
    }

    @Async
    public List<Category> getCategoryAll() {
        var categories = categoryRepository.findAll();
        return categories;
    }

    @Transactional
    @Async
    public void create(Category category) {
        categoryRepository.save(category);
    }

    public Category findById(int id) {
        var category = this.categoryRepository.findCategoryById(id);
        if (category == null) {
            throw new MyResourceNotFoundException("Category not fou nd with id:" + id);
        }
        return category;
    }

    public List<Category> findByName(String name) {
        var categories = this.categoryRepository.findByNameContainingIgnoreCase(name);
        return categories;
    }

    @Transactional
    @Async
    public void updateById(int id, Category category) {
        var categoryExisting = this.categoryRepository.findCategoryById(id);
        if (categoryExisting == null) {
            throw new MyResourceNotFoundException("Category not fou nd with id:" + id);
        }
        categoryExisting.setName(category.getName());
        this.categoryRepository.save(categoryExisting);

    }

    @Transactional
    @Async
    public void deleteById(int id) {
        var category = this.categoryRepository.findCategoryById(id);
        if (category == null) {
            throw new MyResourceNotFoundException("Category not fou nd with id:" + id);
        }
        this.categoryRepository.deleteById(id);
    }

}
