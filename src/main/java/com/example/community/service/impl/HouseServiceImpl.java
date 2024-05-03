package com.example.community.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.community.entity.House;
import com.example.community.mapper.HouseMapper;
import com.example.community.service.HouseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class HouseServiceImpl extends ServiceImpl<HouseMapper, House> implements HouseService {
    @Override
    public void updateBatchStatus(List<Long> idList, Integer status) {
        // 创建 House 对象，设置需要更新的字段
        House updateEntity = new House();
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
        House updateEntity = new House();
        updateEntity.setStatus(status);
        updateEntity.setId(id);

        // 使用 MyBatis-Plus 提供的单独更新方法
        this.updateById(updateEntity);
    }
}
