package com.springboot.crudpractice.item.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductOptionResponseDto {
    private long detailId;
    private long itemId;
    private String option;
    private int quantity;
}
