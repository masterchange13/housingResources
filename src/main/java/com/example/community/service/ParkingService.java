package com.example.community.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.community.dto.ParkingDto;
import com.example.community.entity.Parking;

import java.util.List;

public interface ParkingService extends IService<Parking> {
    void updateBatchStatus(List<Long> idList, Integer status);
    void updateStatus(Long id, Integer status);
}
