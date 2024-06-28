package com.springboot.crudpractice.item.domain;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class Item {
    private Long itemId;
    private String name;
    private int price;
    private String category;
    private String image;
    private String information;
    private String measurment;

    @Builder
    public Item(Long itemId, String name, int price, String category, String image, String information, String measurment) {
        this.itemId = itemId;
        this.name = name;
        this.price = price;
        this.category = category;
        this.image = image;
        this.information = information;
        this.measurment = measurment;
    }
}
