package com.springboot.crudpractice.item.dto;

import com.springboot.crudpractice.item.domain.ItemDetail;

import java.util.Collections;
import java.util.List;

public class ProductOptionResponseDto {
    private List<ItemDetail> itemDetails;

    public ProductOptionResponseDto(List<ItemDetail> itemDetails) {
        this.itemDetails = Collections.unmodifiableList(itemDetails);
    }
}
