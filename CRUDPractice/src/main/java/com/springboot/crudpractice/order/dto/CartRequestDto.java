package com.springboot.crudpractice.order.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CartRequestDto {
    private Long userId;
    private String status;
}
