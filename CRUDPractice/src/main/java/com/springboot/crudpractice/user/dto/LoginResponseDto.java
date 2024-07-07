package com.springboot.crudpractice.user.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class LoginResponseDto {
    private long userId;
    private String name;
}
