package com.codekeys.saas.response;

import com.codekeys.saas.entities.TypeMvt;
import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class StockMvtResponse {
    private TypeMvt typeMvt;
    private Integer quantity;
    private LocalDate dateMvt;
    private String comment;
}
