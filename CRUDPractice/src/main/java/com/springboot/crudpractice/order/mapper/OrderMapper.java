package com.springboot.crudpractice.order.mapper;

import com.springboot.crudpractice.order.domain.Order;
import com.springboot.crudpractice.order.dto.CartRequestDto;
import com.springboot.crudpractice.order.dto.CartResponseDto;
import com.springboot.crudpractice.order.dto.CartUpdateDto;
import com.springboot.crudpractice.order.dto.PickRequestDto;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface OrderMapper {

    @Select("SELECT COUNT(*) " +
            "FROM orders")
    int countOrders();

    @Insert("INSERT INTO orders " +
            "(`user_id`, `status`, `option`, `quantity`, `price`, `created_at`, `modified_at`) " +
            "VALUES " +
            "(#{userId}, #{status}, #{option}, #{quantity}, #{price}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "orderId")
    void saveOrder(PickRequestDto pickRequestDto);

    @Update("UPDATE orders " +
            "SET `option` = #{option}, `quantity` = #{quantity}, `modified_at` = NOW() " +
            "WHERE `order_id` = #{orderId} AND `status`= 'cart'")
    void updateOrderOfWhichStatusIsCart(CartUpdateDto cartUpdateDto);

    @Select("SELECT `order_id`, `user_id`, `status`, `option`, `quantity`, `price` " +
            "FROM orders " +
            "WHERE `order_id` = #{orderId}")
    Order findOrderByOrderId(CartUpdateDto cartUpdateDto);

    @Select("SELECT `order_id`, `status`, `option` ,`quantity`, `price` " +
            "FROM orders " +
            "WHERE `user_id` = #{userId} AND `status`= 'cart'")
    List<Order> findOrdersOfWhichStatusAreCartByUserId(CartRequestDto cartRequestDto);
}
