package com.setec.su54_pos_api.repositorys;

import com.setec.su54_pos_api.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    public Category findCategoryById(int id);

    public List<Category> findByNameContainingIgnoreCase(String name);

}
