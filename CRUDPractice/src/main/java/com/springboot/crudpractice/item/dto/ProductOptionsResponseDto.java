package com.springboot.crudpractice.item.dto;

import com.springboot.crudpractice.item.domain.ItemDetail;
import lombok.Getter;

import java.util.Collections;
import java.util.List;

@Getter
public class ProductOptionsResponseDto {
    private List<ItemDetail> itemDetails;

    public ProductOptionsResponseDto(List<ItemDetail> itemDetails) {
        this.itemDetails = Collections.unmodifiableList(itemDetails);
    }
}
