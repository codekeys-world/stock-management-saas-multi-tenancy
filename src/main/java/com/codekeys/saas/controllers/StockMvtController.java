package com.codekeys.saas.controllers;

import com.codekeys.saas.common.PageResponse;
import com.codekeys.saas.request.StockMvtRequest;
import com.codekeys.saas.response.CategoryResponse;
import com.codekeys.saas.response.StockMvtResponse;
import com.codekeys.saas.services.StockMvtService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/stocks")
@RequiredArgsConstructor
public class StockMvtController {
    private StockMvtService stockMvtService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> createStockMvt(
            @RequestBody
            @Valid
            final StockMvtRequest request
    ){
        this.stockMvtService.create(request);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{stock-mvt-id}")
    public ResponseEntity<Void> updateStockMvt(
            @RequestBody
            @Valid
            final StockMvtRequest request,
            @PathVariable("stock-mvt-id")
            @NotNull(message = "Stock Mvt ID cannot be null")
            final String id
    ){
        this.stockMvtService.update(id, request);
        return ResponseEntity.accepted().build();
    }

    @GetMapping("/{stock-mvt-id}")
    public ResponseEntity<StockMvtResponse> findCategoryById(
            @PathVariable("stock-mvt-id")
            @NotNull(message = "Stock id cannot be null")
            final String id
    ){
        return ResponseEntity.ok(this.stockMvtService.findById(id));
    }

    @GetMapping
    public ResponseEntity<PageResponse<StockMvtResponse>> findAllCategories(
            @RequestParam(name = "page", defaultValue = "0")
            final int page,
            @RequestParam(name = "size", defaultValue = "10")
            final int size
    ){
        return ResponseEntity.ok(this.stockMvtService.findAll(page, size));
    }

    @DeleteMapping("/{stock-mvt-id}")
    public ResponseEntity<Void> deleteMapping(
            @PathVariable("stock-mvt-id")
            @NotNull(message = "Stock id cannot be null")
            final String id
    ){
        this.stockMvtService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
