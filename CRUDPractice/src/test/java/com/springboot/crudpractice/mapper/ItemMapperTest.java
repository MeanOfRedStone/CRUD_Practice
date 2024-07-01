package com.springboot.crudpractice.mapper;

import com.springboot.crudpractice.item.domain.Item;
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
    private SqlSession sqlSession;

    @Test
    void saveItem_WhenItemIsValid_ShouldIncreaseItemsCount() {
        Item testItem = Item.builder()
                .name("블루셔츠")
                .price(1_000)
                .category("셔츠")
                .image("image1")
                .information("info1")
                .measurment("measurement1")
                .build();
        int initialCount = countItems();
        int expectedCount =initialCount + NEW_ITEM;

        sqlSession.insert("ItemMapper.saveItem", testItem);

        int actualCount = countItems();
        assertEquals(expectedCount, actualCount);
    }

    private int countItems() {
        return sqlSession.selectOne("ItemMapper.countItems");
    }

    @Test
    void findAllItems_ShouldReturnAllItems() {
        Item testItem1 = Item.builder()
                .name("블루셔츠")
                .price(1_000)
                .category("셔츠")
                .image("image1")
                .information("info1")
                .measurment("measurement1")
                .build();
        sqlSession.insert("ItemMapper.saveItem", testItem1);
        Item testItem2 = Item.builder()
                .name("청바지")
                .price(2_000)
                .category("바지")
                .image("image2")
                .information("info2")
                .measurment("measurement2")
                .build();
        sqlSession.insert("ItemMapper.saveItem", testItem2);
        int expectedCount = countItems();

        List<Item> items = sqlSession.selectList("ItemMapper.findAllItems");

        int actualCount = items.size();
        assertEquals(expectedCount, actualCount);
    }

    @Test
    void findItemByItemId_WhenItemExists_ShouldReturnItem() {
        Item testItem = Item.builder()
                .name("블루셔츠")
                .price(1_000)
                .category("셔츠")
                .image("image1")
                .information("info1")
                .measurment("measurement1")
                .build();
        sqlSession.insert("ItemMapper.saveItem", testItem);
        Long generatedItemId = testItem.getItemId();

        Item fetchedItem = sqlSession.selectOne("ItemMapper.findItemByItemId", generatedItemId);

        assertNotNull(fetchedItem);
        assertEquals(generatedItemId, fetchedItem.getItemId());
    }
}
