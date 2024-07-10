package com.springboot.crudpractice.orderedItem.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OrderedItemRequestDto {
 private long itemId;
 private long orderId;
}