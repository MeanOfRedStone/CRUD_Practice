package com.springboot.crudpractice.order.mapper;

import com.springboot.crudpractice.item.domain.Item;
import com.springboot.crudpractice.order.domain.Order;
import com.springboot.crudpractice.order.dto.CartRequestDto;
import com.springboot.crudpractice.order.dto.CartResponseDto;
import com.springboot.crudpractice.order.dto.CartUpdateDto;
import com.springboot.crudpractice.order.dto.PickRequestDto;
import com.springboot.crudpractice.order.mapper.OrderMapper;
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

import java.util.List;

@SpringBootTest
@Transactional
public class OrderMapperTest {
    private static final int NEW_ORDER = 1;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private UserMapper userMapper;

    @Test
    void saveOrder_WhenOrderIsValid_ShouldIncreaseOrdersCount() {
        Long generatedId = getTestUser().getUserId();
        PickRequestDto pickRequestDto =PickRequestDto.builder()
                .userId(generatedId)
                .status("cart")
                .option("L")
                .quantity(3)
                .price(1_000)
                .build();
        int initialCount = orderMapper.countOrders();
        int expectedCount = initialCount + NEW_ORDER;

        orderMapper.saveOrder(pickRequestDto);

        int actualCount = orderMapper.countOrders();
        assertEquals(expectedCount, actualCount);
    }

    private JoinRequestDto getTestUser() {
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
        return joinRequestDto;
    }


    @Test
    void updateOrderOfWhichStatusIsCart_WhenOrderExists_ShouldUpdateOrder() {
        Long generatedId = getTestUser().getUserId();
        PickRequestDto pickRequestDto = PickRequestDto.builder()
                .userId(generatedId)
                .status("cart")
                .option("M")
                .quantity(3)
                .price(1_000)
                .build();
        orderMapper.saveOrder(pickRequestDto);

        int expectedQuantity = 1;
        CartUpdateDto cartUpdateDto = CartUpdateDto.builder()
                .orderId(pickRequestDto.getOrderId())
                .userId(generatedId)
                .status("cart")
                .option("M")
                .quantity(expectedQuantity)
                .price(1_000)
                .build();
        orderMapper.updateOrderOfWhichStatusIsCart(cartUpdateDto);

        Order fetechedOrder = orderMapper.findOrderByOrderId(cartUpdateDto);
        assertEquals(expectedQuantity, fetechedOrder.getQuantity());
    }

    @Test
    void findOrderByOrderId_WhenOrderExists_ShouldReturnOrder() {
        Long generatedId = getTestUser().getUserId();
        PickRequestDto pickRequestDto =PickRequestDto.builder()
                .userId(generatedId)
                .status("cart")
                .option("L")
                .quantity(3)
                .price(1_000)
                .build();
        orderMapper.saveOrder(pickRequestDto);
        Long expectedOrderId = pickRequestDto.getOrderId();

        CartUpdateDto cartUpdateDto =CartUpdateDto.builder()
                .orderId(pickRequestDto.getOrderId())
                .userId(generatedId)
                .status("cart")
                .option("L")
                .quantity(3)
                .price(1_000)
                .build();
        Order fetchedOrder = orderMapper.findOrderByOrderId(cartUpdateDto);
        Long actualOrderId = fetchedOrder.getOrderId();

        assertNotNull(fetchedOrder);
        assertEquals(expectedOrderId, actualOrderId);
    }

    @Test
    void findOrdersOfWhichStatusAreCartByUserId_WhenOrderExists_ShouldReturnOrder() {
        Long generatedId = getTestUser().getUserId();

        CartResponseDto cartResponseDto = new CartResponseDto(orderMapper.findOrdersOfWhichStatusAreCartByUserId(CartRequestDto.builder().userId(generatedId).build()));

        assertNotNull(cartResponseDto);
        for (Order order : cartResponseDto.getCartList()) {
            assertEquals(order.getUserId(), generatedId);
        }
    }
}
