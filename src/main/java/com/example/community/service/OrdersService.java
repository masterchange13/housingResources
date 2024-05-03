package com.example.community.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.community.dto.Orders;

public interface OrdersService extends IService<Orders> {
    Orders findByOrderId(Long orderId);
}
