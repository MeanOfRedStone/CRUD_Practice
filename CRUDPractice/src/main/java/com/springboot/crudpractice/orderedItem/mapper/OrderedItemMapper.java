package com.springboot.crudpractice.orderedItem.mapper;

import com.springboot.crudpractice.orderedItem.dto.OrderedItemRequestDto;
import com.springboot.crudpractice.orderedItem.dto.OrderedItemResponseDto;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface OrderedItemMapper {
    @Select("SELECT COUNT(*)" +
            "FROM ordered_items")
    int countOrderedItems();

    @Insert("INSERT INTO ordered_items" +
            "(`order_id`, `item_id`, `created_at`, `modified_at`)" +
            "VALUES" +
            "(#{orderId}, #{itemId}, NOW(), NOW())")
    void saveOrderedItem(OrderedItemRequestDto orderedItemRequestDto);

    @Select("SELECT `order_id`, `item_id`" +
            "FROM ordered_items")
    OrderedItemResponseDto findOrderedItemByOrderIdAndItemId(OrderedItemRequestDto orderedItemRequestDto);
}
