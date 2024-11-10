package com.returdev.product.dtos.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import org.springframework.data.domain.Sort;

/**
 * Data Transfer Object (DTO) for pagination requests.
 * This DTO is used to specify pagination parameters for querying paged results, including the page number,
 * page size, sort direction, and order by field. Default values are applied when parameters are not provided.
 *
 * @param page The page number to retrieve. Must be at least 1.
 * @param pageSize The number of items per page. Must be at least 1 and no more than DEFAULT_PAGE_SIZE (20).
 * @param sortDirection The direction for sorting results, either "ASC" or "DESC" (case insensitive).
 * @param orderBy The field by which to order results, either "ID" or "NAME" (case insensitive).
 */
public record PaginationRequestDTO(
        @Min(value = DEFAULT_PAGE, message = "{validation.min_value.message}")
        Integer page,

        @Min(value = 1, message = "{validation.min_value.message}")
        @Max(value = DEFAULT_PAGE_SIZE, message = "{validation.max_value.message}")
        Integer pageSize,

        @Pattern(regexp = "ASC|DESC", message = "{validation.pagination_sort_direction.message}", flags = Pattern.Flag.CASE_INSENSITIVE)
        String sortDirection,

        @Pattern(regexp = "ID|NAME", message = "{validation.pagination_order_by.message}", flags = Pattern.Flag.CASE_INSENSITIVE)
        String orderBy
) {

    private static final int DEFAULT_PAGE = 1;
    private static final int DEFAULT_PAGE_SIZE = 20;
    private static final String DEFAULT_SORT_DIRECTION = Sort.Direction.ASC.name();
    private static final String DEFAULT_ORDER_BY = "ID";

    /**
     * Constructs a PaginationRequestDTO with default values applied for null arguments.
     * If any argument is null, a default value is used: page defaults to 1, pageSize to 20,
     * sortDirection to "ASC", and orderBy to "ID".
     *
     * @param page The page number, default is 1 if null.
     * @param pageSize The number of items per page, default is 20 if null.
     * @param sortDirection The sorting direction, either "ASC" or "DESC", default is "ASC" if null.
     * @param orderBy The field to sort by, either "ID" or "NAME", default is "ID" if null.
     */
    public PaginationRequestDTO(Integer page, Integer pageSize, String sortDirection, String orderBy) {
        this.page = (page != null) ? page : DEFAULT_PAGE;
        this.pageSize = (pageSize != null) ? pageSize : DEFAULT_PAGE_SIZE;
        this.sortDirection = (sortDirection != null) ? sortDirection : DEFAULT_SORT_DIRECTION;
        this.orderBy = (orderBy != null) ? orderBy : DEFAULT_ORDER_BY;
    }
}

