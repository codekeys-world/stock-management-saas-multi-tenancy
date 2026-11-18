package com.codekeys.saas.request;

import com.codekeys.saas.entities.Product;
import com.codekeys.saas.entities.TypeMvt;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class StockMvtRequest {
    @NotBlank(message = "Type of mouvement should not be empty")
    private TypeMvt typeMvt;
    @Positive(message = "Quantity should not be positive")
    private Integer quantity;
    @NotNull(message = "Date of mouvement should not be empty")
    @PastOrPresent(message = "Date of mouvement should be in the past or present")
    private LocalDate dateMvt;
    private String comment;
    @NotBlank(message = "Product ID should not be empty")
    private String productId;
}
