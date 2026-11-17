package com.codekeys.saas.controllers;

import com.codekeys.saas.common.PageResponse;
import com.codekeys.saas.request.CategoryRequest;
import com.codekeys.saas.response.CategoryResponse;
import com.codekeys.saas.services.CategoryService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> createCategory(
            @RequestBody
            @Valid
            final CategoryRequest request
    ){
        this.categoryService.create(request);
        return ResponseEntity.ok()
                .build();
    }

    @PutMapping("/{category-id}")
    public ResponseEntity<Void> createCategory(
            @RequestBody
            @Valid
            final CategoryRequest request,
            @PathVariable("category-id")
            @NotNull(message = "Category id cannot be null")
            final String id
    ){
        this.categoryService.update(id, request);
        return ResponseEntity.accepted()
                .build();
    }

    @GetMapping("/{category-id}")
    public ResponseEntity<CategoryResponse> findCategoryById(
            @PathVariable("category-id")
            @NotNull(message = "Category id cannot be null")
            final String id
    ){
        return ResponseEntity.ok(this.categoryService.findById(id));
    }

    @GetMapping
    public ResponseEntity<PageResponse<CategoryResponse>> findAllCategories(
            @RequestParam(name = "page", defaultValue = "0")
            final int page,
            @RequestParam(name = "size", defaultValue = "10")
            final int size
    ){
        return ResponseEntity.ok(this.categoryService.findAll(page, size));
    }

    @DeleteMapping("/{category-id}")
    public ResponseEntity<Void> deleteMapping(
            @PathVariable("category-id")
            @NotNull(message = "Category id cannot be null")
            final String id
    ){
        this.categoryService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
