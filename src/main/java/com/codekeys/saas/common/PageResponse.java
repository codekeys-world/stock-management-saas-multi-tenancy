package com.codekeys.saas.common;

import lombok.*;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PageResponse<T> {
    private List<T> content;
    private int page;
    private int size;
    private long totalElements;
    private int totalPages;
    private boolean hasNext;
    private boolean hasPrevious;
    private boolean isFirst;
    private boolean isLast;

    public static <T> PageResponse<T> of(final Page<T> page){
        return PageResponse.<T>builder()
                .content(page.getContent())
                .size(page.getSize())
                .totalPages(page.getTotalPages())
                .totalElements(page.getTotalElements())
                .hasNext(page.hasNext())
                .hasPrevious(page.hasPrevious())
                .isFirst(page.isFirst())
                .isLast(page.isLast())
                .build();
    }
}
