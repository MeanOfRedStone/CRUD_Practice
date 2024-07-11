package com.springboot.crudpractice.item.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductOptionUpdateRequestDto {
    private long detailId;
    private long itemId;
    private long status;
    private long quantity;
}
