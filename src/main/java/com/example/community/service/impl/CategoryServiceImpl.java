package com.example.community.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.community.common.CustomException;
import com.example.community.entity.Category;
import com.example.community.entity.House;
import com.example.community.entity.Parking;
import com.example.community.mapper.CategoryMapper;
import com.example.community.service.CategoryService;
import com.example.community.service.HouseService;
import com.example.community.service.ParkingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper,Category> implements CategoryService{

    @Autowired
    private HouseService houseService;

    @Autowired
    private ParkingService parkingService;

    /**
     * 根据id删除小区，删除之前需要进行判断
     * @param id
     */
    @Override
    public void remove(Long id) {

        LambdaQueryWrapper<House> houseLambdaQueryWrapper = new LambdaQueryWrapper<>();
        //添加查询条件，根据分类id进行查询
        houseLambdaQueryWrapper.eq(House::getCategoryId,id);
        int count1 = houseService.count(houseLambdaQueryWrapper);
        //查询当前分类是否关联了房屋，如果已经关联，抛出一个业务异常
        if(count1 > 0){
            //已经关联房屋，抛出一个业务异常
            throw new CustomException("当前分类下关联了房屋，不能删除");
        }

        //查询当前分类是否关联了车位，如果已经关联，抛出一个业务异常
        LambdaQueryWrapper<Parking> parkingLambdaQueryWrapper = new LambdaQueryWrapper<>();
        //添加查询条件，根据分类id进行查询
        parkingLambdaQueryWrapper.eq(Parking::getCategoryId,id);
        int count2 = parkingService.count(parkingLambdaQueryWrapper);
        if(count2 > 0){
            //已经关联车位，抛出一个业务异常
            throw new CustomException("当前分类下关联了车位，不能删除");
        }

        //正常删除分类
        super.removeById(id);
    }
}
