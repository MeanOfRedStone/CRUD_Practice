package com.springboot.crudpractice.mapper;

import com.springboot.crudpractice.user.domain.User;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;
import static org.junit.Assert.assertEquals;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class UserMapperTest {
    public static final int NEW_USER = 1;

    @Autowired
    private SqlSession sqlSession;

    @Test
    @Rollback
    void testSaveUser() {
        User testUser = User.builder().id("admin").password("admin").name("admin").email("admin").phone("999").address("seoul")
                .addressDetail("Jongno").agreement(1).phoneContact(1).emailContact(1).build();
        int count = countUsers();
        int expectedCount = count + NEW_USER;

        sqlSession.insert("UserMapper.saveUser", testUser);
        int actualCount = countUsers();

        assertEquals(expectedCount, actualCount);
    }

    private int countUsers() {
        return sqlSession.selectOne("UserMapper.countUsers");
    }
}
