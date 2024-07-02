package com.springboot.crudpractice.item.domain;

import lombok.*;

@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class ItemDetail {
    private long detailId;
    private long itemId;
    private String option;
    private int quantity;

    @Builder
    public ItemDetail(long detailId, long itemId, String option, int quantity) {
        this.detailId = detailId;
        this.itemId = itemId;
        this.option = option;
        this.quantity = quantity;
    }
}
