package com.springboot.crudpractice.mapper;

import com.springboot.crudpractice.item.domain.Item;
import com.springboot.crudpractice.order.domain.Order;
import com.springboot.crudpractice.user.domain.User;
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
        Order testOrder =Order.builder()
                .userId(generatedId)
                .status("cart")
                .option("L")
                .quantity(3)
                .price(1_000)
                .build();
        int initialCount = countOrders();
        int expectedCount = initialCount + NEW_ORDER;

        sqlSession.insert("OrderMapper.saveOrder", testOrder);

        int actualCount = countOrders();
        assertEquals(expectedCount, actualCount);
    }

    private int countOrders() {
        return sqlSession.selectOne("OrderMapper.countOrders");
    }

    @Test
    void updateOrderOfWhichStatusIsCart_WhenOrderExists_ShouldUpdateOrder() {
        Long generatedId = getTestUser().getUserId();

        String expectedOption = "M";
        Order updatedOrder = Order.builder()
                .userId(generatedId)
                .status("cart")
                .option(expectedOption)
                .quantity(3)
                .price(1_000)
                .build();

        sqlSession.update("OrderMapper.updateOrderOfWhichStatusIsCart", updatedOrder);
        String actualOption = updatedOrder.getOption();

        assertEquals(expectedOption, actualOption);
    }

    @Test
    void findOrdersOfWhichStatusAreCartByUserId_WhenOrderExists_ShouldReturnOrder() {
        Long generatedId = getTestUser().getUserId();

        List<Order> fetchedOrders = sqlSession.selectList("OrderMapper.findOrdersOfWhichStatusAreCartByUserId", generatedId);

        assertNotNull(fetchedOrders);
        for (Order order : fetchedOrders) {
            assertEquals(order.getUserId(), generatedId);
        }
    }

    private User getTestUser() {
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
        return testUser;
    }
}
