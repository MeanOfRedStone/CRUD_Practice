package com.springboot.crudpractice.user.mapper;

import com.springboot.crudpractice.user.dto.JoinRequestDto;
import com.springboot.crudpractice.user.dto.LoginRequestDto;
import com.springboot.crudpractice.user.dto.LoginResponseDto;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    @Insert("INSERT INTO users (id, password, name, email, phone, address, address_detail, " +
            "agreement, phone_contact, email_contact, created_at, modified_at)" +
            "VALUES (#{id}, #{password}, #{name}, #{email}, #{phone}, #{address}, #{addressDetail}, " +
            "#{agreement}, #{phoneContact}, #{emailContact}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "userId")
    void saveUser(JoinRequestDto joinRequestDto);

    @Select("SELECT COUNT(*) FROM users")
    int countUsers();

    @Select("SELECT user_id, name FROM users WHERE id = #{id} AND password = #{password}")
    LoginResponseDto findUser(LoginRequestDto loginRequestDto);
}
