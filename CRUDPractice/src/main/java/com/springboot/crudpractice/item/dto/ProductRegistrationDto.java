package com.springboot.crudpractice.item.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductRegistrationDto {
    private long itemId;
    private String name;
    private int price;
    private String category;
    private String image;
    private String information;
    private String measurment;
}
