package com.springboot.crudpractice.mapper;

import com.springboot.crudpractice.item.domain.Item;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;
import static org.junit.Assert.assertEquals;
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
    void testSaveItem() {
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
}
