package com.codekeys.saas.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ProductRequest {
    @NotBlank(message = "Product name should not be empty")
    @Size(min = 3, max = 255, message = "Product name should be between 3 and 255 characters")
    private String name;
    @NotBlank(message = "Product reference should not be empty")
    @Size(min = 3, max = 255, message = "Product reference should be between 3 and 255 characters")
    private String reference;
    private String description;
    @Positive(message = "Alert Threshold should not be positive")
    private Integer alertThreshold;
    @Positive(message = "Price should not be positive")
    private BigDecimal price;
    @NotBlank(message = "Category ID should not be empty")
    private String categoryId;
}
