package com.springboot.crudpractice.user.service;

import com.springboot.crudpractice.user.dto.JoinRequestDto;

public interface UserService {

    void addUser(JoinRequestDto joinRequestDto);

}
