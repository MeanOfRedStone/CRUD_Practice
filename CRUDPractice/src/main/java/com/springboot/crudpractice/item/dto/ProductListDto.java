package com.springboot.crudpractice.item.dto;

import com.springboot.crudpractice.item.domain.Item;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter

public class ProductListDto {
    private final List<Item> productList;

    public ProductListDto(List<Item> productList) {
        this.productList = Collections.unmodifiableList(productList);
    }
}
