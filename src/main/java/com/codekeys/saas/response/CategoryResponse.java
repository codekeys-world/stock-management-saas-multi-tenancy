package com.codekeys.saas.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CategoryResponse {
    private String name;
    private String description;
    private int nbProducts;
}
