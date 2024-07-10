package com.springboot.crudpractice.item.dto;

import com.springboot.crudpractice.item.domain.Item;
import lombok.Getter;

import java.util.Collections;
import java.util.List;

@Getter
public class ProductListResponseDto {
    private final List<Item> productList;

    public ProductListResponseDto(List<Item> productList) {
        this.productList = Collections.unmodifiableList(productList);
    }
}
