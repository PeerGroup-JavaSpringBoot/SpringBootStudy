package dev.aquashdw.community.service;

import dev.aquashdw.community.controller.dto.CategoryDto;
import dev.aquashdw.community.entity.CategoryEntity;
import dev.aquashdw.community.repository.CategoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    private static final Logger logger = LoggerFactory.getLogger(CategoryService.class);
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public CategoryDto createCategory(CategoryDto dto){
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setName(dto.getName());
        categoryEntity = this.categoryRepository.save(categoryEntity);
        return new CategoryDto(categoryEntity);
    }

    public CategoryDto readCategory(Long id) {
        Optional<CategoryEntity> categoryEntityOptional = this.categoryRepository.findById(id);
        if (categoryEntityOptional.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        return new CategoryDto(categoryEntityOptional.get());
    }

    public List<CategoryDto> readCategoryAll(){
        List<CategoryDto> categoryDtoList = new ArrayList<>();
        this.categoryRepository.findAll().forEach(categoryEntity ->
                categoryDtoList.add(new CategoryDto(categoryEntity)));
        return categoryDtoList;
    }
}
