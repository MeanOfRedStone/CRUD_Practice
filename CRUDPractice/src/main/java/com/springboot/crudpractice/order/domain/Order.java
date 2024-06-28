package com.springboot.crudpractice.order.domain;

import lombok.*;

@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class Order {
    private Long orderId;
    private Long userId;
    private String status;
    private String option;
    private int quantity;
    private int price;

    @Builder
    public Order(Long orderId, Long userId, String status, String option, int quantity, int price) {
        this.orderId = orderId;
        this.userId = userId;
        this.status = status;
        this.option = option;
        this.quantity = quantity;
        this.price = price;
    }
}
