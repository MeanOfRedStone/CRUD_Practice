package com.springboot.crudpractice.mapper;

import com.springboot.crudpractice.item.domain.Item;
import com.springboot.crudpractice.item.domain.ItemDetail;
import com.springboot.crudpractice.item.dto.ProductOptionRegistrationRequestDto;
import com.springboot.crudpractice.item.dto.ProductOptionRequestDto;
import com.springboot.crudpractice.item.dto.ProductOptionResponseDto;
import com.springboot.crudpractice.item.dto.ProductRegistrationRequestDto;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;
import static org.junit.Assert.assertEquals;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@Transactional
public class ItemDetailMapperTest {
    private static final int NEW_ITEM_DETAIL = 1;
    @Autowired
    SqlSession sqlSession;

    @Test
    void saveItemDetail_WhenItemDetailIsValid_ShouldIncreaseItemsDetailCount() {
        int expectedCount = countItemsDetail() + NEW_ITEM_DETAIL;

        ProductOptionRegistrationRequestDto productOptionRegistrationRequestDto = ProductOptionRegistrationRequestDto.builder()
                .itemId(getItemId())
                .option("L")
                .quantity(5)
                .build();
        sqlSession.insert("ItemDetailMapper.saveItemDetail", productOptionRegistrationRequestDto);

        int actualCount = countItemsDetail();
        assertEquals(expectedCount, actualCount);
    }

    private int countItemsDetail() {
        return sqlSession.selectOne("ItemDetailMapper.countItemsDetail");
    }

    @Test
    void findAllItemDetailByItemId_WhenItemDetailExist_ShouldReturnItemDetail() {
        Long generatedItemId = getItemId();
        saveTestItemDetail(generatedItemId, "L", 5);
        saveTestItemDetail(generatedItemId, "M", 3);
        int expectedCount = 2 * NEW_ITEM_DETAIL;

        ProductOptionResponseDto productOptionResponseDto = new ProductOptionResponseDto(sqlSession.selectList("ItemDetailMapper.findAllItemDetailByItemId", ProductOptionRequestDto.builder().itemId(generatedItemId).build()));

        assertEquals(expectedCount, productOptionResponseDto.getItemDetails().size());
    }

    private void saveTestItemDetail(Long itemId, String option, int quantity) {
        ProductOptionRegistrationRequestDto productOptionRegistrationRequestDto = ProductOptionRegistrationRequestDto.builder()
                .itemId(itemId)
                .option(option)
                .quantity(quantity)
                .build();

        sqlSession.insert("ItemDetailMapper.saveItemDetail", productOptionRegistrationRequestDto);
    }

    @Test
    void findItemDetailByDetailId_WhenItemDetailExists_ShouldReturnItemDetail() {
        Long generatedItemId = getItemId();
        ItemDetail testItemDetail = ItemDetail.builder()
                .itemId(generatedItemId)
                .option("L")
                .quantity(3)
                .build();
        sqlSession.insert("ItemDetailMapper.saveItemDetail", testItemDetail);

        ItemDetail fetechedItemDetail = sqlSession.selectOne("ItemDetailMapper.findItemDetailByDetailId", testItemDetail.getDetailId());

        assertEquals(testItemDetail.getDetailId(), fetechedItemDetail.getDetailId());
    }

    @Test
    void updateItemDetail_WhenItemDetailExists_ShouldUpdateItemDetail() {
        Long generatedItemId = getItemId();
        ItemDetail testItemDetail = ItemDetail.builder()
                .itemId(generatedItemId)
                .option("L")
                .quantity(5)
                .build();
        sqlSession.insert("ItemDetailMapper.saveItemDetail", testItemDetail);

        int changedQuantity = 0;
        ItemDetail updatedItemDetail = ItemDetail.builder()
                .detailId(testItemDetail.getDetailId())
                .itemId(generatedItemId)
                .option("L")
                .quantity(changedQuantity)
                .build();
        sqlSession.update("ItemDetailMapper.updateItemDetail", updatedItemDetail);

        ItemDetail fetchedItemDetail = sqlSession.selectOne("ItemDetailMapper.findItemDetailByDetailId", testItemDetail.getDetailId());
        assertEquals(changedQuantity, fetchedItemDetail.getQuantity());
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
        sqlSession.insert("ItemMapper.saveItem", productRegistrationRequestDto);

        return productRegistrationRequestDto.getItemId();
    }
}
