package com.codekeys.saas.services.impl;

import com.codekeys.saas.common.PageResponse;
import com.codekeys.saas.entities.Product;
import com.codekeys.saas.entities.StockMvt;
import com.codekeys.saas.mapper.StockMvtMapper;
import com.codekeys.saas.repositories.ProductRepository;
import com.codekeys.saas.repositories.StockMvtStkRepository;
import com.codekeys.saas.request.StockMvtRequest;
import com.codekeys.saas.response.ProductResponse;
import com.codekeys.saas.response.StockMvtResponse;
import com.codekeys.saas.services.StockMvtService;
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
public class StockMvtServiceImpl implements StockMvtService {
    private final StockMvtStkRepository stockMvtStkRepository;
    private final StockMvtMapper stockMvtMapper;
    private final ProductRepository productRepository;

    @Override
    public void create(StockMvtRequest request) {
        checkIfProductExistsById(request.getProductId());
        final StockMvt entity = this.stockMvtMapper.toEntity(request);
        this.stockMvtStkRepository.save(entity);
    }

    @Override
    public void update(String id, StockMvtRequest request) {
        final Optional<StockMvt> stockMvt = this.stockMvtStkRepository.findById(id);
        if(stockMvt.isEmpty()){
            log.debug("StockMvt does not exist");
            throw new EntityNotFoundException("StockMvt does not exist");
        }
        checkIfProductExistsById(request.getProductId());
        final StockMvt stockMvtToUpdate = this.stockMvtMapper.toEntity(request);
        stockMvtToUpdate.setId(id);
        this.stockMvtStkRepository.save(stockMvtToUpdate);
    }

    @Override
    public PageResponse<StockMvtResponse> findAll(int page, int size) {
        final PageRequest pageRequest = PageRequest.of(page, size);
        final Page<StockMvt> stockMvts = this.stockMvtStkRepository.findAll(pageRequest);
        final Page<StockMvtResponse> stockMvtResponses = stockMvts.map(stockMvtMapper::toResponse);
        return PageResponse.of(stockMvtResponses);
    }

    @Override
    public StockMvtResponse findById(String id) {
        return this.stockMvtStkRepository.findById(id)
                .map(this.stockMvtMapper::toResponse)
                .orElseThrow(() -> new EntityNotFoundException("Stock mouvement does not exist"));
    }

    @Override
    public void delete(String id) {
        final StockMvt stockMvt = this.stockMvtStkRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Stock mouvement does not exist"));
        this.stockMvtStkRepository.delete(stockMvt);
    }

    private void checkIfProductExistsById(final String productId){
        final Optional<Product> product = this.productRepository.findById(productId);
        if(product.isEmpty()){
            log.debug("Product does not exists");
            throw new EntityNotFoundException("Product does not exist");
        }
    }
}
