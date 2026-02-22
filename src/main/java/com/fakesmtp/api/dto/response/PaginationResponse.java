package com.fakesmtp.api.dto.response;

import org.springframework.data.domain.Page;

public record PaginationResponse(
        Integer page,
        Integer size,
        Long totalElements,
        Integer numberOfElements,
        Integer totalPages
) {

    public PaginationResponse(Page<?> page) {
        this(
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getNumberOfElements(),
                page.getTotalPages()
        );
    }

}
