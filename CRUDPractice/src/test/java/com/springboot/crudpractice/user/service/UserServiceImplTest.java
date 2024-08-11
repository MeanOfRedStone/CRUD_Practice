package com.springboot.crudpractice.user.service;

import com.springboot.crudpractice.user.dto.JoinRequestDto;
import com.springboot.crudpractice.user.mapper.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;


@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {
    private static final int NEW_USER = 1;

    @InjectMocks
    private UserServiceImpl userServiceImpl;

    @Mock
    private UserMapper userMapper;

    @Test
    public void addUser_WhenJoinRequestDtoIsValid_ShouldCallSaveUser() {
        JoinRequestDto joinRequestDto = JoinRequestDto.builder().id("admin").password("admin").name("admin").email("admin").phone("999").address("seoul")
                .addressDetail("Jongno").agreement(1).phoneContact(1).emailContact(1).build();
        Mockito.doNothing().when(userMapper).saveUser(joinRequestDto);

        userServiceImpl.addUser(joinRequestDto);

        Mockito.verify(userMapper, Mockito.times(1)).saveUser(joinRequestDto);
    }
}
