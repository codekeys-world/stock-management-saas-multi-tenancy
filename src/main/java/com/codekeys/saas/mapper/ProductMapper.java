package com.codekeys.saas.mapper;

import com.codekeys.saas.entities.Category;
import com.codekeys.saas.entities.Product;
import com.codekeys.saas.request.ProductRequest;
import com.codekeys.saas.response.ProductResponse;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {
    public Product toEntity(final ProductRequest request){
        Category category = new Category();
        category.setId(request.getCategoryId());
        return Product.builder()
                .name(request.getName())
                .reference(request.getReference())
                .description(request.getDescription())
                .price(request.getPrice())
                .alertThreshold(request.getAlertThreshold())
                .category(category).build();
    }

    public ProductResponse toResponse(final Product product){
        return ProductResponse.builder()
                .name(product.getName())
                .reference(product.getReference())
                .description(product.getDescription())
                .price(product.getPrice())
                .alertThreshold(product.getAlertThreshold())
                .categoryName(product.getCategory().getName())
                //.availableQuantity() To be implemented later
                .build();
    }
}
