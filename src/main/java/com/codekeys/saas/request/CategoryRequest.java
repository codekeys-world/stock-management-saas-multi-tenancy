package com.codekeys.saas.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CategoryRequest {
    private String name;
    private String description;
}
