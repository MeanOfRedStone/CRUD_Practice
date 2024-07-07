package com.springboot.crudpractice.mapper;

import com.springboot.crudpractice.user.domain.User;
import com.springboot.crudpractice.user.dto.JoinRequestDto;
import com.springboot.crudpractice.user.dto.LoginRequestDto;
import com.springboot.crudpractice.user.dto.LoginResponseDto;
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
        JoinRequestDto joinRequestDto = JoinRequestDto.builder().id("admin").password("admin").name("admin").email("admin").phone("999").address("seoul")
                .addressDetail("Jongno").agreement(1).phoneContact(1).emailContact(1).build();
        int count = countUsers();
        int expectedCount = count + NEW_USER;

        sqlSession.insert("UserMapper.saveUser", joinRequestDto);
        int actualCount = countUsers();
        assertEquals(expectedCount, actualCount);
    }

    private int countUsers() {
        return sqlSession.selectOne("UserMapper.countUsers");
    }

    @Test
    void findUser_WhenIdAndPasswordAreCorrect_ShouldReturnUser() {
        JoinRequestDto joinRequestDto = JoinRequestDto.builder().id("admin").password("admin").name("admin").email("admin").phone("999").address("seoul")
                .addressDetail("Jongno").agreement(1).phoneContact(1).emailContact(1).build();
        sqlSession.insert("UserMapper.saveUser", joinRequestDto);
        long expectedUserId = joinRequestDto.getUserId();

        LoginRequestDto loginRequestDto = LoginRequestDto.builder().id("admin").password("admin").build();
        LoginResponseDto loginResponseDto = sqlSession.selectOne("UserMapper.findUser", loginRequestDto);
        long actualUserId = loginResponseDto.getUserId();

        assertNotNull(loginResponseDto);
        assertEquals(expectedUserId, actualUserId);
    }

}
