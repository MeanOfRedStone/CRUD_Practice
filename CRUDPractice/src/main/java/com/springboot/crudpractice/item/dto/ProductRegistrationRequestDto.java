package com.springboot.crudpractice.item.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductRegistrationRequestDto {
    private long itemId;
    private String name;
    private int price;
    private String category;
    private String image;
    private String information;
    private String measurment;
}
