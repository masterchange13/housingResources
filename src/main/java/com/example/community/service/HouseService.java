package com.example.community.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.community.dto.HouseDto;
import com.example.community.entity.House;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface HouseService extends IService<House> {

    void updateBatchStatus(List<Long> idList, Integer status);

    void updateStatus(Long id, Integer status);
}
