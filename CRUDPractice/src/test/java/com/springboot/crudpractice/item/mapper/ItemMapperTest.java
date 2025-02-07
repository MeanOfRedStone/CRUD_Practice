package com.springboot.crudpractice.item.mapper;

import com.springboot.crudpractice.item.domain.Item;
import com.springboot.crudpractice.item.dto.ProductDetailRequestDto;
import com.springboot.crudpractice.item.dto.ProductDetailResponseDto;
import com.springboot.crudpractice.item.dto.ProductListResponseDto;
import com.springboot.crudpractice.item.dto.ProductRegistrationRequestDto;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@Transactional
public class ItemMapperTest {
    private static final int NEW_ITEM = 1;

    @Autowired
    private ItemMapper itemMapper;

    @Test
    void saveItem_WhenItemIsValid_ShouldIncreaseItemsCount() {
        ProductRegistrationRequestDto productRegistrationDto = ProductRegistrationRequestDto.builder()
                .name("블루셔츠")
                .price(1_000)
                .category("셔츠")
                .image("image1")
                .information("info1")
                .measurment("measurement1")
                .build();
        int initialCount = itemMapper.countItems();
        int expectedCount =initialCount + NEW_ITEM;

        itemMapper.saveItem(productRegistrationDto);

        int actualCount = itemMapper.countItems();
        assertEquals(expectedCount, actualCount);
    }

    @Test
    void findAllItems_ShouldReturnAllItems() {
        int initialCount = itemMapper.countItems();
        int expectedCount = initialCount + 2 * NEW_ITEM;
        ProductRegistrationRequestDto productRegistrationDto = ProductRegistrationRequestDto.builder()
                .name("블루셔츠")
                .price(1_000)
                .category("셔츠")
                .image("image1")
                .information("info1")
                .measurment("measurement1")
                .build();
        itemMapper.saveItem(productRegistrationDto);
        ProductRegistrationRequestDto productRegistrationDto2 = ProductRegistrationRequestDto.builder()
                .name("청바지")
                .price(2_000)
                .category("바지")
                .image("image2")
                .information("info2")
                .measurment("measurement2")
                .build();
        itemMapper.saveItem(productRegistrationDto2);

        ProductListResponseDto productListResponseDto = new ProductListResponseDto(itemMapper.findAllItems());

        int actualCount = productListResponseDto.getProductList().size();
        assertEquals(expectedCount, actualCount);
    }

    @Test
    void findItemByItemId_WhenItemExists_ShouldReturnItem() {
        ProductRegistrationRequestDto productRegistrationRequestDto = ProductRegistrationRequestDto.builder()
                .name("블루셔츠")
                .price(1_000)
                .category("셔츠")
                .image("image1")
                .information("info1")
                .measurment("measurement1")
                .build();
        itemMapper.saveItem(productRegistrationRequestDto);
        ProductDetailRequestDto productDetailRequestDto = ProductDetailRequestDto.builder().itemId(productRegistrationRequestDto.getItemId()).build();

        ProductDetailResponseDto productDetailResponseDto = itemMapper.findByItemId(productDetailRequestDto);

        assertNotNull(productDetailResponseDto);
        assertEquals(productDetailRequestDto.getItemId(), productDetailResponseDto.getItemId());
    }
}
