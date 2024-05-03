package com.example.community.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.community.common.R;
import com.example.community.dto.Orders;
import com.example.community.entity.House;
import com.example.community.service.HouseService;
import com.example.community.service.OrdersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Random;

@RestController
@Slf4j
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrdersService ordersService;
    @Autowired
    private HouseService houseService;


    /**
     * 房屋信息分页查询
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize){

        //构造分页构造器对象
        //分页构造器
        Page<Orders> pageInfo = new Page<>(page,pageSize);
        //条件构造器
        LambdaQueryWrapper<Orders> queryWrapper = new LambdaQueryWrapper<>();
        //添加排序条件，根据sort进行排序
        queryWrapper.orderByAsc(Orders::getPrice);

        //分页查询
        ordersService.page(pageInfo,queryWrapper);
        return R.success(pageInfo);
    }

    @PostMapping
    public R<String> delete(Long id){
        log.info("订单，id为：{}", id);
        // 生成随机订单 ID
        Long randomId = generateRandomId();
        Orders orders = new Orders();
        orders.setOrderId(randomId); // 将随机 ID 设置到订单 DTO 中
        House house = houseService.getById(id);
        orders.setGoodsName(house.getName());
        orders.setPrice(house.getPrice());
        orders.setStatus(1);
        ordersService.save(orders);
        return R.success("订单新添成功");
    }

    private Long generateRandomId() {
        Random random = new Random();
        // 生成一个范围在 1 到 Long.MAX_VALUE 之间的随机数作为订单 ID
        return (long) (random.nextInt(Integer.MAX_VALUE - 1) + 1);
    }
}



