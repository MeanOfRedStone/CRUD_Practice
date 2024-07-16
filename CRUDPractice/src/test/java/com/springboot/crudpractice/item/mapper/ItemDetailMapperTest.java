package com.springboot.crudpractice.item.mapper;

import com.springboot.crudpractice.item.domain.ItemDetail;
import com.springboot.crudpractice.item.dto.*;
import com.springboot.crudpractice.item.mapper.ItemDetailMapper;
import com.springboot.crudpractice.item.mapper.ItemMapper;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;
import static org.junit.Assert.assertEquals;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class ItemDetailMapperTest {
    private static final int NEW_ITEM_DETAIL = 1;

    @Autowired
    private ItemDetailMapper itemDetailMapper;
    @Autowired
    private ItemMapper itemMapper;

    @Test
    void saveItemDetail_WhenItemDetailIsValid_ShouldIncreaseItemsDetailCount() {
        int expectedCount = itemDetailMapper.countItemsDetail() + NEW_ITEM_DETAIL;

        ProductOptionRegistrationRequestDto productOptionRegistrationRequestDto = ProductOptionRegistrationRequestDto.builder()
                .itemId(getItemId())
                .option("L")
                .quantity(5)
                .build();
        itemDetailMapper.saveItemDetail(productOptionRegistrationRequestDto);

        int actualCount = itemDetailMapper.countItemsDetail();
        assertEquals(expectedCount, actualCount);
    }

    private long getItemId() {
        ProductRegistrationRequestDto productRegistrationRequestDto = ProductRegistrationRequestDto.builder()
                .name("블루셔츠")
                .price(1_000)
                .category("셔츠")
                .image("image1")
                .information("info1")
                .measurment("measurement1")
                .build();
        itemMapper.saveItem(productRegistrationRequestDto);

        return productRegistrationRequestDto.getItemId();
    }


    @Test
    void findAllItemDetailByItemId_WhenItemDetailExist_ShouldReturnItemDetail() {
        Long generatedItemId = getItemId();
        saveTestItemDetail(generatedItemId, "L", 5);
        saveTestItemDetail(generatedItemId, "M", 3);
        int expectedCount = 2 * NEW_ITEM_DETAIL;

        ProductOptionsResponseDto productOptionsResponseDto = new ProductOptionsResponseDto(itemDetailMapper.findAllItemDetailByItemId(ProductOptionRequestDto.builder().itemId(generatedItemId).build()));

        assertEquals(expectedCount, productOptionsResponseDto.getItemDetails().size());
    }

    private void saveTestItemDetail(Long itemId, String option, int quantity) {
        ProductOptionRegistrationRequestDto productOptionRegistrationRequestDto = ProductOptionRegistrationRequestDto.builder()
                .itemId(itemId)
                .option(option)
                .quantity(quantity)
                .build();

        itemDetailMapper.saveItemDetail(productOptionRegistrationRequestDto);
    }

    @Test
    void findItemDetailByDetailId_WhenItemDetailExists_ShouldReturnItemDetail() {
        Long generatedItemId = getItemId();
        ProductOptionRegistrationRequestDto productOptionRegistrationRequestDto = ProductOptionRegistrationRequestDto.builder()
                .itemId(generatedItemId)
                .option("L")
                .quantity(3)
                .build();
        itemDetailMapper.saveItemDetail(productOptionRegistrationRequestDto);

        ProductOptionUpdateRequestDto productOptionUpdateRequestDto = ProductOptionUpdateRequestDto.builder().detailId(productOptionRegistrationRequestDto.getDetailId()).build();

        ProductOptionResponseDto productOptionResponseDto = itemDetailMapper.findItemDetailByDetailId(productOptionUpdateRequestDto);

        assertEquals(productOptionRegistrationRequestDto.getDetailId(), productOptionResponseDto.getDetailId());
    }

    @Test
    void updateItemDetail_WhenItemDetailExists_ShouldUpdateItemDetail() {
        Long generatedItemId = getItemId();
        ProductOptionRegistrationRequestDto productOptionRegistrationRequestDto = ProductOptionRegistrationRequestDto.builder()
                .itemId(generatedItemId)
                .option("L")
                .quantity(5)
                .build();
        itemDetailMapper.saveItemDetail(productOptionRegistrationRequestDto);

        int changedQuantity = 0;
        ProductOptionUpdateRequestDto productOptionUpdateRequestDto = ProductOptionUpdateRequestDto.builder()
                .detailId(productOptionRegistrationRequestDto.getDetailId())
                .itemId(generatedItemId)
                .option("L")
                .quantity(changedQuantity)
                .build();
        itemDetailMapper.updateItemDetail(productOptionUpdateRequestDto);

        ProductOptionResponseDto productOptionResponseDto = itemDetailMapper.findItemDetailByDetailId(productOptionUpdateRequestDto);
        assertEquals(changedQuantity, productOptionResponseDto.getQuantity());
    }

}
