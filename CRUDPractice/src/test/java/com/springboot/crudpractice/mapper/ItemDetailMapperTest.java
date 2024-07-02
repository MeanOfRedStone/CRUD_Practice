package com.springboot.crudpractice.mapper;

import com.springboot.crudpractice.item.domain.Item;
import com.springboot.crudpractice.item.domain.ItemDetail;
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

        ItemDetail testItemDetail = ItemDetail.builder()
                .itemId(getItemId())
                .option("L")
                .quantity(5)
                .build();
        sqlSession.insert("ItemDetailMapper.saveItemDetail", testItemDetail);

        int actualCount = countItemsDetail();
        assertEquals(expectedCount, actualCount);
    }

    private int countItemsDetail() {
        return sqlSession.selectOne("ItemDetailMapper.countItemsDetail");
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
