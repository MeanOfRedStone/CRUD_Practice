package com.springboot.crudpractice.mapper;

import com.springboot.crudpractice.item.domain.Item;
import com.springboot.crudpractice.order.domain.Order;
import com.springboot.crudpractice.orderedItem.domain.OrderedItem;
import com.springboot.crudpractice.user.domain.User;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;
import static org.junit.Assert.assertEquals;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class OrderedItemMapperTest {
    private static final int NEW_ORDERED_ITEM = 1;

    @Autowired
    private SqlSession sqlSession;

    @Test
    void saveOrderedItem_WhenOrderedItemIsValid_ShouldIncreaseItemCount(){
        OrderedItem testOrderedItem = OrderedItem.builder()
                .orderId(getOrderId())
                .itemId(getItemId())
                .build();
        int expectedCount = countOrderedItems() + NEW_ORDERED_ITEM;

        sqlSession.insert("OrderedItemMapper.saveOrderedItem", testOrderedItem);

        int actualCount = countOrderedItems();
        assertEquals(expectedCount, actualCount);
    }

    private int countOrderedItems() {
        return sqlSession.selectOne("OrderedItemMapper.countOrderedItems");
    }

    private long getUserId() {
        User testUser = User.builder()
                .id("admin")
                .password("password")
                .name("admin")
                .phone("010-0000-0000")
                .email("email@email.com")
                .address("Jongno")
                .addressDetail("dong")
                .agreement(1)
                .emailContact(1)
                .phoneContact(1)
                .build();
        sqlSession.insert("UserMapper.saveUser", testUser);
        return testUser.getUserId();
    }

    private long getOrderId() {
        Order testOrder =Order.builder()
                .userId(getUserId())
                .status("cart")
                .option("L")
                .quantity(3)
                .price(1_000)
                .build();
        sqlSession.insert("OrderMapper.saveOrder", testOrder);
        return testOrder.getOrderId();
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
