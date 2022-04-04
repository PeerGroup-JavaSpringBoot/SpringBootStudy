package dev.aquashdw.community.controller;

import dev.aquashdw.community.controller.dto.CategoryDto;
import dev.aquashdw.community.service.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("category")
public class CategoryController {
    private static final Logger logger = LoggerFactory.getLogger(CategoryController.class);
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public ResponseEntity<CategoryDto> createCategory(@RequestBody CategoryDto categoryDto){
        return ResponseEntity.ok(this.categoryService.createCategory(categoryDto));
    }

    @GetMapping("{id}")
    public ResponseEntity<CategoryDto> readCategory(@PathVariable("id") Long id) {
        return ResponseEntity.ok(this.categoryService.readCategory(id));
    }

    @GetMapping
    public ResponseEntity<Collection<CategoryDto>> readCategoryAll(){
        return ResponseEntity.ok(this.categoryService.readCategoryAll());
    }
}
