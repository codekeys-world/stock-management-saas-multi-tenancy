package com.codekeys.saas.services.impl;

import com.codekeys.saas.common.PageResponse;
import com.codekeys.saas.entities.Category;
import com.codekeys.saas.exceptions.DuplicateRessourceException;
import com.codekeys.saas.mapper.CategoryMapper;
import com.codekeys.saas.repositories.CategoryRepository;
import com.codekeys.saas.request.CategoryRequest;
import com.codekeys.saas.response.CategoryResponse;
import com.codekeys.saas.services.CategoryService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public void create(CategoryRequest request) {
        //Check if category already exist
        checkIfCategoryExist(request.getName());
        final Category entity = this.categoryMapper.toEntity(request);
        this.categoryRepository.save(entity);
    }

    @Override
    public void update(String id, CategoryRequest request) {
        //Check if category already exist
        final Optional<Category> existingCategory = this.categoryRepository.findById(id);
        if(existingCategory.isEmpty()){
            log.info("Category does not exist");
            throw new EntityNotFoundException("Category does not exist");
        }
        checkIfCategoryExist(request.getName());
        final Category categoryToUpdate = this.categoryMapper.toEntity(request);
        categoryToUpdate.setId(id);
        this.categoryRepository.save(categoryToUpdate);
    }

    @Override
    public PageResponse<CategoryResponse> findAll(int page, int size) {
        final PageRequest pageRequest = PageRequest.of(page, size);
        final Page<Category> categories = this.categoryRepository.findAll(pageRequest);
        final Page<CategoryResponse> categoryResponses = categories.map(categoryMapper::toResponse);
        return PageResponse.of(categoryResponses);
    }

    @Override
    public CategoryResponse findById(String id) {
        return this.categoryRepository.findById(id)
                .map(this.categoryMapper::toResponse)
                .orElseThrow(() -> new EntityNotFoundException("Category does not exist"));
    }

    @Override
    public void delete(String id) {
        final Category category = this.categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category does not exist"));
        this.categoryRepository.delete(category);
    }

    private void checkIfCategoryExist(String name) {
        final Optional<Category> category = this.categoryRepository.findByNameIgnoreCase(name);
        if(category.isPresent()){
            log.info("Category already exist");
            throw new DuplicateRessourceException("Category already exits");
        }
    }
}
