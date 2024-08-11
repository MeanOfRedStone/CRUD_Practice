package com.springboot.crudpractice.user.service;

import com.springboot.crudpractice.user.dto.JoinRequestDto;
import com.springboot.crudpractice.user.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public void addUser(JoinRequestDto joinRequestDto) {
        userMapper.saveUser(joinRequestDto);
    }
}
