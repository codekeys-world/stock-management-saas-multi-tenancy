package com.codekeys.saas.services.impl;

import com.codekeys.saas.common.PageResponse;
import com.codekeys.saas.entities.Category;
import com.codekeys.saas.entities.Product;
import com.codekeys.saas.mapper.ProductMapper;
import com.codekeys.saas.repositories.CategoryRepository;
import com.codekeys.saas.repositories.ProductRepository;
import com.codekeys.saas.request.ProductRequest;
import com.codekeys.saas.response.CategoryResponse;
import com.codekeys.saas.response.ProductResponse;
import com.codekeys.saas.services.CategoryService;
import com.codekeys.saas.services.ProductService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final CategoryRepository categoryRepository;


    @Override
    public void create(ProductRequest request) {
        // Check if product already exist
        checkIfProductAlreadyExistByReference(request.getReference());
        checkIfCategoryExistById(request.getCategoryId());
        final Product entity = this.productMapper.toEntity(request);
        this.productRepository.save(entity);
    }

    @Override
    public void update(String id, ProductRequest request) {
        // Check if product already exist
        final Optional<Product> productExist = this.productRepository.findById(id);
        if(productExist.isEmpty()){
            log.debug("Product does not exist");
            throw new EntityNotFoundException("Product does not exist");
        }
        //check if product already exists
        checkIfProductAlreadyExistByReference(request.getReference());
        //check if category exists
        checkIfCategoryExistById(request.getCategoryId());
        final Product productToUpdate = this.productMapper.toEntity(request);
        productToUpdate.setId(id);
        this.productRepository.save(productToUpdate);
    }

    @Override
    public PageResponse<ProductResponse> findAll(int page, int size) {
        final PageRequest pageRequest = PageRequest.of(page, size);
        final Page<Product> products = this.productRepository.findAll(pageRequest);
        final Page<ProductResponse> productResponses = products.map(productMapper::toResponse);
        return PageResponse.of(productResponses);
    }

    @Override
    public ProductResponse findById(String id) {
        return this.productRepository.findById(id)
                .map(this.productMapper::toResponse)
                .orElseThrow(() -> new EntityNotFoundException("Product does not exist"));
    }

    @Override
    public void delete(String id) {
        final Product product = this.productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product does not exist"));
        this.productRepository.delete(product);
    }

    private void checkIfProductAlreadyExistByReference(String reference){
        final Optional<Product> product = this.productRepository.findByReferenceIgnoreCase(reference);
        if(product.isPresent()){
            log.debug("Product already exist");
            throw new RuntimeException("Product already exist");
        }
    }

    private void checkIfCategoryExistById(final String categoryId){
        final Optional<Category> category = this.categoryRepository.findById(categoryId);
        if(category.isEmpty()){
            log.debug("Category does not exist");
            throw new EntityNotFoundException("Category does not exist");
        }
    }
}
