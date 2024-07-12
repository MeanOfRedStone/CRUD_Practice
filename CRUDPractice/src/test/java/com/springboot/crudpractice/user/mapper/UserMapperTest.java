package com.springboot.crudpractice.user.mapper;

import com.springboot.crudpractice.user.dto.JoinRequestDto;
import com.springboot.crudpractice.user.dto.LoginRequestDto;
import com.springboot.crudpractice.user.dto.LoginResponseDto;
import org.junit.jupiter.api.Test;
import static org.junit.Assert.assertNotNull;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;

@SpringBootTest
@Transactional
public class UserMapperTest {
    private static final int NEW_USER = 1;

    @Autowired
    private UserMapper userMapper;

    @Test
    void saveUser_WhenUserIsValid_ShouldIncreaseUsersCount() {
        JoinRequestDto joinRequestDto = JoinRequestDto.builder().id("admin").password("admin").name("admin").email("admin").phone("999").address("seoul")
                .addressDetail("Jongno").agreement(1).phoneContact(1).emailContact(1).build();
        int count = userMapper.countUsers();
        int expectedCount = count + NEW_USER;

        userMapper.saveUser(joinRequestDto);
        int actualCount = userMapper.countUsers();
        assertEquals(expectedCount, actualCount);
    }

    @Test
    void findUser_WhenIdAndPasswordAreCorrect_ShouldReturnLoginResponseDto() {
        JoinRequestDto joinRequestDto = JoinRequestDto.builder().id("admin").password("admin").name("admin").email("admin").phone("999").address("seoul")
                .addressDetail("Jongno").agreement(1).phoneContact(1).emailContact(1).build();
        userMapper.saveUser(joinRequestDto);

        LoginRequestDto loginRequestDto = LoginRequestDto.builder().id("admin").password("admin").build();
        LoginResponseDto loginResponseDto = userMapper.findUser(loginRequestDto);

        assertNotNull(loginResponseDto);
        assertEquals(joinRequestDto.getUserId(), loginResponseDto.getUserId());
    }

}
