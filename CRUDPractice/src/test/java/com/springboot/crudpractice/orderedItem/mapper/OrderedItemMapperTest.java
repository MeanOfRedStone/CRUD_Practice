package com.springboot.crudpractice.orderedItem.mapper;

import com.springboot.crudpractice.item.domain.Item;
import com.springboot.crudpractice.item.dto.ProductRegistrationRequestDto;
import com.springboot.crudpractice.item.mapper.ItemMapper;
import com.springboot.crudpractice.order.domain.Order;
import com.springboot.crudpractice.order.dto.PickRequestDto;
import com.springboot.crudpractice.order.mapper.OrderMapper;
import com.springboot.crudpractice.orderedItem.domain.OrderedItem;
import com.springboot.crudpractice.orderedItem.dto.OrderedItemRequestDto;
import com.springboot.crudpractice.orderedItem.dto.OrderedItemResponseDto;
import com.springboot.crudpractice.user.domain.User;
import com.springboot.crudpractice.user.dto.JoinRequestDto;
import com.springboot.crudpractice.user.mapper.UserMapper;
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
    private OrderedItemMapper orderedItemMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private ItemMapper itemMapper;

    @Test
    void saveOrderedItem_WhenOrderedItemIsValid_ShouldIncreaseItemCount(){
        OrderedItemRequestDto orderedItemRequestDto = OrderedItemRequestDto.builder()
                .orderId(getOrderId())
                .itemId(getItemId())
                .build();
        int expectedCount = orderedItemMapper.countOrderedItems() + NEW_ORDERED_ITEM;

        orderedItemMapper.saveOrderedItem(orderedItemRequestDto);

        int actualCount = orderedItemMapper.countOrderedItems();
        assertEquals(expectedCount, actualCount);
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
        userMapper.saveUser(joinRequestDto);
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
        orderMapper.saveOrder(pickRequestDto);
        return pickRequestDto.getOrderId();
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
    void findOrderedItemByOrderIdAndItemId_WhenOrderedItemExists_ShouldReturnOrderedItem(){
        OrderedItemRequestDto orderedItemRequestDto = OrderedItemRequestDto.builder()
                .orderId(getOrderId())
                .itemId(getItemId())
                .build();
        orderedItemMapper.saveOrderedItem(orderedItemRequestDto);

        OrderedItemResponseDto orderedItemResponseDto = orderedItemMapper.findOrderedItemByOrderIdAndItemId(OrderedItemRequestDto.builder().itemId(orderedItemRequestDto.getItemId()).orderId(orderedItemRequestDto.getOrderId()).build());

        assertNotNull(orderedItemResponseDto);
        assertEquals(orderedItemRequestDto.getItemId(), orderedItemResponseDto.getItemId());
        assertEquals(orderedItemRequestDto.getOrderId(), orderedItemResponseDto.getOrderId());
    }
}
