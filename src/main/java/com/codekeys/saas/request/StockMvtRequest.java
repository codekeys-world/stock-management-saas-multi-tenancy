package com.codekeys.saas.request;

import com.codekeys.saas.entities.Product;
import com.codekeys.saas.entities.TypeMvt;
import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class StockMvtRequest {
    private TypeMvt typeMvt;
    private Integer quantity;
    private LocalDate dateMvt;
    private String comment;
    private String productId;
}
