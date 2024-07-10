package com.springboot.crudpractice.mapper;

import com.springboot.crudpractice.item.domain.Item;
import com.springboot.crudpractice.item.dto.ProductListDto;
import com.springboot.crudpractice.item.dto.ProductRegistrationDto;
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
        ProductRegistrationDto productRegistrationDto = ProductRegistrationDto.builder()
                .name("블루셔츠")
                .price(1_000)
                .category("셔츠")
                .image("image1")
                .information("info1")
                .measurment("measurement1")
                .build();
        int initialCount = countItems();
        int expectedCount =initialCount + NEW_ITEM;

        sqlSession.insert("ItemMapper.saveItem", productRegistrationDto);

        int actualCount = countItems();
        assertEquals(expectedCount, actualCount);
    }

    private int countItems() {
        return sqlSession.selectOne("ItemMapper.countItems");
    }

    @Test
    void findAllItems_ShouldReturnAllItems() {
        int initialCount = countItems();
        int expectedCount = initialCount + 2 * NEW_ITEM;
        ProductRegistrationDto productRegistrationDto = ProductRegistrationDto.builder()
                .name("블루셔츠")
                .price(1_000)
                .category("셔츠")
                .image("image1")
                .information("info1")
                .measurment("measurement1")
                .build();
        sqlSession.insert("ItemMapper.saveItem", productRegistrationDto);
        ProductRegistrationDto productRegistrationDto2 = ProductRegistrationDto.builder()
                .name("청바지")
                .price(2_000)
                .category("바지")
                .image("image2")
                .information("info2")
                .measurment("measurement2")
                .build();
        sqlSession.insert("ItemMapper.saveItem", productRegistrationDto2);


        List<Item> fetchedItems = sqlSession.selectList("ItemMapper.findAllItems");
        ProductListDto productListDto = new ProductListDto(fetchedItems);

        int actualCount = productListDto.getProductList().size();
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
