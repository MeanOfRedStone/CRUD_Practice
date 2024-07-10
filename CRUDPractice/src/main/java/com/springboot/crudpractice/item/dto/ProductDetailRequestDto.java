package com.springboot.crudpractice.item.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductDetailRequestDto {
    private long itemId;
}
