package com.springboot.crudpractice.item.mapper;


import com.springboot.crudpractice.item.domain.ItemDetail;
import com.springboot.crudpractice.item.dto.ProductOptionRegistrationRequestDto;
import com.springboot.crudpractice.item.dto.ProductOptionRequestDto;
import com.springboot.crudpractice.item.dto.ProductOptionResponseDto;
import com.springboot.crudpractice.item.dto.ProductOptionUpdateRequestDto;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ItemDetailMapper {

    @Select("SELECT COUNT(*) FROM items_detail")
    int countItemsDetail();

    @Insert("INSERT INTO items_detail (`item_id`, `option`, `quantity`, `created_at`, `modified_at`) " +
            "VALUES (#{itemId}, #{option}, #{quantity}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "detailId")
    void saveItemDetail(ProductOptionRegistrationRequestDto productOptionRegistrationRequestDto);

    @Select("SELECT `item_id`, `detail_id`, `option`, `quantity` " +
            "FROM items_detail " +
            "WHERE `item_id` = #{itemId}")
    List<ItemDetail> findAllItemDetailByItemId(ProductOptionRequestDto productOptionRequestDto);

    @Select("SELECT `detail_id`, `item_id`, `option`, `quantity` " +
            "FROM items_detail " +
            "WHERE `detail_id` = #{detailId}")
    ProductOptionResponseDto findItemDetailByDetailId(ProductOptionUpdateRequestDto productOptionUpdateRequestDto);

    @Update("UPDATE items_detail " +
            "SET `quantity` = #{quantity}, `option` = #{option}, `modified_at` = NOW() " +
            "WHERE `detail_id` = #{detailId}")
    void updateItemDetail(ProductOptionUpdateRequestDto productOptionUpdateRequestDto);
}
