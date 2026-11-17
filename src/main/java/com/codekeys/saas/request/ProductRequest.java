package com.codekeys.saas.request;

import com.codekeys.saas.entities.Category;
import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ProductRequest {
    private String name;
    private String reference;
    private String description;
    private Integer alertThreshold;
    private BigDecimal price;
    private String categoryId;
}
