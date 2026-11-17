package com.codekeys.saas.mapper;

import com.codekeys.saas.entities.Product;
import com.codekeys.saas.entities.StockMvt;
import com.codekeys.saas.request.StockMvtRequest;
import com.codekeys.saas.response.StockMvtResponse;
import org.springframework.stereotype.Component;

@Component
public class StockMvtMapper {
    public StockMvt toEntity(final StockMvtRequest request){
        Product product = new Product();
        product.setId(request.getProductId());
        return StockMvt.builder()
                .dateMvt(request.getDateMvt())
                .comment(request.getComment())
                .typeMvt(request.getTypeMvt())
                .quantity(request.getQuantity())
                .product(product)
                .build();
    }

    public StockMvtResponse toResponse(final StockMvt stockMvt){
        return StockMvtResponse.builder()
                .dateMvt(stockMvt.getDateMvt())
                .comment(stockMvt.getComment())
                .typeMvt(stockMvt.getTypeMvt())
                .quantity(stockMvt.getQuantity())
                .build();
    }
}
