package com.springboot.crudpractice.mapper;

import com.springboot.crudpractice.item.domain.Item;
import com.springboot.crudpractice.order.domain.Order;
import com.springboot.crudpractice.order.dto.CartResponseDto;
import com.springboot.crudpractice.order.dto.CartUpdateDto;
import com.springboot.crudpractice.order.dto.PickRequestDto;
import com.springboot.crudpractice.user.domain.User;
import com.springboot.crudpractice.user.dto.JoinRequestDto;
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
    private SqlSession sqlSession;

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
        int initialCount = countOrders();
        int expectedCount = initialCount + NEW_ORDER;

        sqlSession.insert("OrderMapper.saveOrder", pickRequestDto);

        int actualCount = countOrders();
        assertEquals(expectedCount, actualCount);
    }

    private int countOrders() {
        return sqlSession.selectOne("OrderMapper.countOrders");
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
        sqlSession.insert("OrderMapper.saveOrder", pickRequestDto);

        int expectedQuantity = 1;
        CartUpdateDto cartUpdateDto = CartUpdateDto.builder()
                .orderId(pickRequestDto.getOrderId())
                .userId(generatedId)
                .status("cart")
                .option("M")
                .quantity(expectedQuantity)
                .price(1_000)
                .build();
        sqlSession.update("OrderMapper.updateOrderOfWhichStatusIsCart", cartUpdateDto);

        Order fetechedOrder = sqlSession.selectOne("OrderMapper.findOrderByOrderId", cartUpdateDto.getOrderId());
        assertEquals(expectedQuantity, fetechedOrder.getQuantity());
    }

    @Test
    void findOrdersOfWhichStatusAreCartByUserId_WhenOrderExists_ShouldReturnOrder() {
        Long generatedId = getTestUser().getUserId();

        CartResponseDto cartResponseDto = new CartResponseDto(sqlSession.selectList("OrderMapper.findOrdersOfWhichStatusAreCartByUserId", generatedId));

        assertNotNull(cartResponseDto);
        for (Order order : cartResponseDto.getCartList()) {
            assertEquals(order.getUserId(), generatedId);
        }
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
        sqlSession.insert("UserMapper.saveUser", joinRequestDto);
        return joinRequestDto;
    }

    @Test
    void findOrderByOrderId_WhenOrderExists_ShouldReturnOrder() {
        Long generatedId = getTestUser().getUserId();
        Order testOrder =Order.builder()
                .userId(generatedId)
                .status("cart")
                .option("L")
                .quantity(3)
                .price(1_000)
                .build();
        sqlSession.insert("OrderMapper.saveOrder", testOrder);
        Long expectedOrderId = testOrder.getOrderId();

        Order fetchedOrder = sqlSession.selectOne("OrderMapper.findOrderByOrderId", testOrder.getOrderId());
        Long actualOrderId = fetchedOrder.getOrderId();

        assertNotNull(fetchedOrder);
        assertEquals(expectedOrderId, actualOrderId);
    }
}
