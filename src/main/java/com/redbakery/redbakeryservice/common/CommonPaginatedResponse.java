package com.redbakery.redbakeryservice.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class CommonPaginatedResponse <T>{
    private T data;
    private Long totalRecords;
    private Integer totalPages;
    private Integer currentPage;
    private Integer pageSize;
}
