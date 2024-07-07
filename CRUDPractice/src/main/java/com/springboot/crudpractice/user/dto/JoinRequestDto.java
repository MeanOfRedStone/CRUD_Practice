package com.springboot.crudpractice.user.dto;

import lombok.*;

@Builder
@Getter
public class JoinRequestDto {
    private long userId;
    private String id;
    private String password;
    private String name;
    private String phone;
    private String email;
    private String address;
    private String addressDetail;
    private int agreement;
    private int phoneContact;
    private int emailContact;
}
