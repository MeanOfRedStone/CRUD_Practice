package com.springboot.crudpractice.orderedItem.domain;

import lombok.*;

@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class OrderedItem {
    private Long orderId;
    private Long itemId;

    @Builder
    public OrderedItem(Long orderId, Long itemId) {
        this.orderId = orderId;
        this.itemId = itemId;
    }
}
