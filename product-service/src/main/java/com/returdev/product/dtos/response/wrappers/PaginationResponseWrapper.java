package com.returdev.product.dtos.response.wrappers;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * Wrapper for paginated responses. Contains a list of paginated data and additional pagination details.
 *
 * @param <T> The type of data contained in the paginated response.
 */
public record PaginationResponseWrapper<T>(
        @JsonProperty("data") List<T> content,
        @JsonProperty("page_info") PageInfo pageInfo
) {

    /**
     * Contains pagination metadata for the response.
     */
    @Data
    @AllArgsConstructor
    public static class PageInfo {

        /**
         * The number of elements per page.
         */
        @JsonProperty(value = "size")
        private int pageSize;

        /**
         * The total number of elements across all pages.
         */
        @JsonProperty(value = "totalElements")
        private long totalElements;

        /**
         * The total number of pages.
         */
        @JsonProperty(value = "totalPages")
        private int totalPages;

        /**
         * The current page number (zero-based).
         */
        @JsonProperty(value = "number")
        private int pageNumber;

    }

}

