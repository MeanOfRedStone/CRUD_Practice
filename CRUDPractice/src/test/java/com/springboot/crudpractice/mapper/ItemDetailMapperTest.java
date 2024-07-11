package com.springboot.crudpractice.mapper;

import com.springboot.crudpractice.item.domain.Item;
import com.springboot.crudpractice.item.domain.ItemDetail;
import com.springboot.crudpractice.item.dto.ProductOptionRequestDto;
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

        ProductOptionRequestDto productOptionRequestDto = ProductOptionRequestDto.builder()
                .itemId(getItemId())
                .option("L")
                .quantity(5)
                .build();
        sqlSession.insert("ItemDetailMapper.saveItemDetail", productOptionRequestDto);

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
        int expectedCount = 2;

        List<ItemDetail> fetchedItemDetails = sqlSession.selectList("ItemDetailMapper.findAllItemDetailByItemId", generatedItemId);

        assertEquals(expectedCount, fetchedItemDetails.size());
    }

    private void saveTestItemDetail(Long itemId, String option, int quantity) {
        ItemDetail testItemDetail = ItemDetail.builder()
                .itemId(itemId)
                .option(option)
                .quantity(quantity)
                .build();

        sqlSession.insert("ItemDetailMapper.saveItemDetail", testItemDetail);
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
        Item testItem = Item.builder()
                .name("블루셔츠")
                .price(1_000)
                .category("셔츠")
                .image("image1")
                .information("info1")
                .measurment("measurement1")
                .build();
        sqlSession.insert("ItemMapper.saveItem", testItem);

        return testItem.getItemId();
    }
}
