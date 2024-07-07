package com.springboot.crudpractice.user.domain;

import lombok.*;

@Getter
@ToString(exclude = "password")
@Builder
public class User {
    private Long userId;
    private String id;
    private String password;
    private String name;
    private String email;
    private String phone;
    private String address;
    private String addressDetail;
    private int agreement;
    private int phoneContact;
    private int emailContact;
}
