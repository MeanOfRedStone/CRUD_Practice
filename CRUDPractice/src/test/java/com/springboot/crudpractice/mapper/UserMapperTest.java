package com.springboot.crudpractice.mapper;

import com.springboot.crudpractice.user.domain.User;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class UserMapperTest {
    public static final int NEW_USER = 1;

    @Autowired
    private SqlSession sqlSession;

    @Test
    void saveUser_WhenUserIsValid_ShouldIncreaseUsersCount() {
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

    @Test
    void findUser_WhenIdAndPasswordAreCorrect_ShouldReturnUser() {
        User adminUser = User.builder().id("admin").password("admin").name("admin").email("admin").phone("999").address("seoul")
                .addressDetail("Jongno").agreement(1).phoneContact(1).emailContact(1).build();
        sqlSession.insert("UserMapper.saveUser", adminUser);

        User loginUser = User.builder().id("admin").password("admin").build();
        User actualUser = sqlSession.selectOne("UserMapper.findUser", loginUser);

        assertNotNull(actualUser);
    }

}
