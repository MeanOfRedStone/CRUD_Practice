package com.springboot.crudpractice.order.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CartUpdateDto {
    private long orderId;
    private long userId;
    private String option;
    private String status;
    private int quantity;
    private int price;
}
