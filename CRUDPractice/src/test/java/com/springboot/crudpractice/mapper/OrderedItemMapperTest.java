package com.springboot.crudpractice.mapper;

import com.springboot.crudpractice.item.domain.Item;
import com.springboot.crudpractice.order.domain.Order;
import com.springboot.crudpractice.order.dto.PickRequestDto;
import com.springboot.crudpractice.orderedItem.domain.OrderedItem;
import com.springboot.crudpractice.orderedItem.dto.OrderedItemRequestDto;
import com.springboot.crudpractice.orderedItem.dto.OrderedItemResponseDto;
import com.springboot.crudpractice.user.domain.User;
import com.springboot.crudpractice.user.dto.JoinRequestDto;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
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
        OrderedItemRequestDto orderedItemRequestDto = OrderedItemRequestDto.builder()
                .orderId(getOrderId())
                .itemId(getItemId())
                .build();
        int expectedCount = countOrderedItems() + NEW_ORDERED_ITEM;

        sqlSession.insert("OrderedItemMapper.saveOrderedItem", orderedItemRequestDto);

        int actualCount = countOrderedItems();
        assertEquals(expectedCount, actualCount);
    }

    @Test
    void findOrderedItemByOrderIdAndItemId_WhenOrderedItemExists_ShouldReturnOrderedItem(){
        OrderedItemRequestDto orderedItemRequestDto = OrderedItemRequestDto.builder()
                .orderId(getOrderId())
                .itemId(getItemId())
                .build();
        sqlSession.insert("OrderedItemMapper.saveOrderedItem", orderedItemRequestDto);

        OrderedItemResponseDto orderedItemResponseDto = sqlSession.selectOne("OrderedItemMapper.findOrderedItemByOrderIdAndItemId", OrderedItem.builder().itemId(orderedItemRequestDto.getItemId()).orderId(orderedItemRequestDto.getOrderId()).build());

        assertNotNull(orderedItemResponseDto);
        assertEquals(orderedItemRequestDto.getItemId(), orderedItemResponseDto.getItemId());
        assertEquals(orderedItemRequestDto.getOrderId(), orderedItemResponseDto.getOrderId());
    }

    private int countOrderedItems() {
        return sqlSession.selectOne("OrderedItemMapper.countOrderedItems");
    }

    private long getUserId() {
        JoinRequestDto joinRequestDto = JoinRequestDto.builder()
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
        sqlSession.insert("UserMapper.saveUser", joinRequestDto);
        return joinRequestDto.getUserId();
    }

    private long getOrderId() {
        PickRequestDto pickRequestDto =PickRequestDto.builder()
                .userId(getUserId())
                .status("cart")
                .option("L")
                .quantity(3)
                .price(1_000)
                .build();
        sqlSession.insert("OrderMapper.saveOrder", pickRequestDto);
        return pickRequestDto.getOrderId();
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
