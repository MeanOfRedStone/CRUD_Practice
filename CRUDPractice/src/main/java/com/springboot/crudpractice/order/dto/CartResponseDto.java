package com.springboot.crudpractice.order.dto;

import com.springboot.crudpractice.order.domain.Order;
import lombok.Builder;
import lombok.Getter;

import java.util.Collections;
import java.util.List;

@Getter
public class CartResponseDto {
    private final List<Order> cartList;

    public CartResponseDto(List<Order> cartList) {
        this.cartList = Collections.unmodifiableList(cartList);
    }
}
