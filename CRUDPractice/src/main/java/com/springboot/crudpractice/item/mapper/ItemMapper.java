package com.springboot.crudpractice.item.mapper;

import com.springboot.crudpractice.item.domain.Item;
import com.springboot.crudpractice.item.dto.ProductDetailRequestDto;
import com.springboot.crudpractice.item.dto.ProductDetailResponseDto;
import com.springboot.crudpractice.item.dto.ProductListResponseDto;
import com.springboot.crudpractice.item.dto.ProductRegistrationRequestDto;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ItemMapper {

    @Select("SELECT COUNT(*) FROM items")
    int countItems();

    @Insert("INSERT INTO items" +
            "(`name`, `price`, `category`, `image`, `information`, `measurment`, " +
            "`created_at`, `modified_at`)" +
            "VALUES" +
            "(#{name}, #{price}, #{category}, #{image}, #{information}, #{measurment}, " +
            "NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "itemId")
    void saveItem(ProductRegistrationRequestDto productRegistrationRequestDto);

    @Select("SELECT item_id, name, price, category, image FROM items")
    List<Item> findAllItems();

    @Select("SELECT item_id, name, price, category, image, information, measurment FROM items WHERE item_id = #{itemId}")
    ProductDetailResponseDto findByItemId(ProductDetailRequestDto productDetailRequestDto);
}
