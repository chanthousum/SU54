package com.setec.su54_pos_api.controllers;

import java.util.HashMap;
import java.util.Map;

import com.setec.su54_pos_api.models.Category;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.setec.su54_pos_api.services.CategoryService;

@RestController
@RequestMapping("/api/v1/category/")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService _categoryService) {
        super();
        this.categoryService = _categoryService;
    }
    @GetMapping("/")
    public ResponseEntity<?> getCategoryAll() {
        var categories = categoryService.getCategoryAll();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @PostMapping("create")
    public ResponseEntity<?> create(@RequestBody Category category) {
        categoryService.create(category);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Category created successfully");
        response.put("data", category);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<?> findById(@PathVariable int id) {
        var category = categoryService.findById(id);
        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    @GetMapping("findByName")
    public ResponseEntity<?> findByName(@RequestParam("name") String name) {
        var categories = categoryService.findByName(name);
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<?> updateById(@PathVariable int id, @RequestBody Category category) {
        this.categoryService.updateById(id, category);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Category updated successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteById(@PathVariable int id) {
        this.categoryService.deleteById(id);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Category delted successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
