package com.codekeys.saas.response;

import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ProductResponse {
    private String name;
    private String reference;
    private String description;
    private Integer alertThreshold;
    private BigDecimal price;
    private String categoryName;
    private int availableQuantity;

}
