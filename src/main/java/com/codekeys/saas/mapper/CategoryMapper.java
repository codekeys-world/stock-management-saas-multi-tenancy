package com.codekeys.saas.mapper;

import com.codekeys.saas.entities.Category;
import com.codekeys.saas.request.CategoryRequest;
import com.codekeys.saas.response.CategoryResponse;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {
    public Category toEntity(final CategoryRequest request){
        return Category.builder()
                .name(request.getName())
                .description(request.getDescription())
                .build();
    }

    public CategoryResponse toResponse(final Category category){
        int nbProduct = 0;//category.getProducts() == null ? 0 : category.getProducts().size();
        return CategoryResponse.builder()
                .name(category.getName())
                .description(category.getDescription())
                .nbProducts(nbProduct)
                .build();
    }
}
