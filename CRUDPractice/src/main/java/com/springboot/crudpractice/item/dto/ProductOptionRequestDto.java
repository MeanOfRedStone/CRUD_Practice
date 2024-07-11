package com.springboot.crudpractice.item.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductOptionRequestDto {
    private long itemId;
    private long detailId;
}
