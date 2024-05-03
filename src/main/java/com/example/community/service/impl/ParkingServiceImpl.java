package com.example.community.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.community.entity.Parking;
import com.example.community.mapper.ParkingMapper;
import com.example.community.service.ParkingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ParkingServiceImpl extends ServiceImpl<ParkingMapper, Parking> implements ParkingService {
    @Override
    public void updateBatchStatus(List<Long> idList, Integer status) {
        // 创建 Parking 对象，设置需要更新的字段
        Parking updateEntity = new Parking();
        updateEntity.setStatus(status);

        // 使用 MyBatis-Plus 提供的批量更新方法
        this.updateBatchById(idList.stream().map(id -> {
            updateEntity.setId(id);
            return updateEntity;
        }).collect(Collectors.toList()));
    }

    @Override
    public void updateStatus(Long id, Integer status) {
        // 创建 House 对象，设置需要更新的字段
        Parking updateEntity = new Parking();
        updateEntity.setStatus(status);
        updateEntity.setId(id);

        // 使用 MyBatis-Plus 提供的单独更新方法
        this.updateById(updateEntity);
    }
}
