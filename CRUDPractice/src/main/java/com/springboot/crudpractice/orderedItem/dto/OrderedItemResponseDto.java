package com.springboot.crudpractice.orderedItem.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OrderedItemResponseDto {
    private long itemId;
    private long orderId;
}
