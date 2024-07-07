package com.springboot.crudpractice.user.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class LoginRequestDto {
    private String id;
    private String password;
}
