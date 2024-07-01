package com.springboot.crudpractice.mapper;

import com.springboot.crudpractice.item.domain.Item;
import com.springboot.crudpractice.order.domain.Order;
import com.springboot.crudpractice.user.domain.User;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;
import static org.junit.Assert.assertEquals;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class OrderMapperTest {
    private static final int NEW_ORDER = 1;

    @Autowired
    private SqlSession sqlSession;

    @Test
    void testSaveOrder() {
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
