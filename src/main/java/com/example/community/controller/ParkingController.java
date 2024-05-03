package com.example.community.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.community.common.R;
import com.example.community.dto.HouseDto;
import com.example.community.dto.ParkingDto;
import com.example.community.entity.Category;
import com.example.community.entity.Parking;
import com.example.community.service.CategoryService;
import com.example.community.service.ParkingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 车位管理
 */

@RestController
@RequestMapping("/parking")
@Slf4j
public class ParkingController {

    @Autowired
    private ParkingService parkingService;

    @Autowired
    private CategoryService categoryService;

    /**
     * 新增车位
     * @param parkingDto
     * @return
     */
    @PostMapping
    public R<String> save(@RequestBody ParkingDto parkingDto){
        log.info("车位信息：{}", parkingDto);

        parkingService.save(parkingDto);

        return R.success("新增车位成功");
    }

    /**
     * 车位分页查询
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public R<Page> page(int page,int pageSize,String name){
        //分页构造器对象
        Page<Parking> pageInfo = new Page<>(page,pageSize);
        Page<ParkingDto> dtoPage = new Page<>();

        LambdaQueryWrapper<Parking> queryWrapper = new LambdaQueryWrapper<>();
        //添加查询条件，根据name进行like模糊查询
        queryWrapper.like(name != null,Parking::getName,name);
        //添加排序条件，根据更新时间降序排列
        queryWrapper.orderByDesc(Parking::getUpdateTime);

        parkingService.page(pageInfo,queryWrapper);

        //对象拷贝
        BeanUtils.copyProperties(pageInfo,dtoPage,"records");
        List<Parking> records = pageInfo.getRecords();

        List<ParkingDto> list = records.stream().map((item) -> {
            ParkingDto parkingDto = new ParkingDto();
            //对象拷贝
            BeanUtils.copyProperties(item, parkingDto);
            //分类id
            Long categoryId = item.getCategoryId();
            //根据分类id查询分类对象
            Category category = categoryService.getById(categoryId);
            if(category != null){
                //分类名称
                String categoryName = category.getName();
                parkingDto.setCategoryName(categoryName);
            }
            return parkingDto;
        }).collect(Collectors.toList());

        dtoPage.setRecords(list);
        return R.success(dtoPage);
    }

    @GetMapping("/{id}")
    public R<ParkingDto> get(@PathVariable Long id){

        Parking parking = parkingService.getById(id);

        ParkingDto parkingDto = new ParkingDto();
        BeanUtils.copyProperties(parking,parkingDto);
        return R.success(parkingDto);
    }

    @PutMapping
    public R<String> update(@RequestBody ParkingDto parkingDto){
        log.info(parkingDto.toString());

        parkingService.updateById(parkingDto);

        return R.success("修改车位成功");
    }
    /**
     * 删除车位
     * @param id
     * @return
     */
    @DeleteMapping
    public R<String> delete(Long id){
        log.info("删除车位，id为：{}",id);
        parkingService.removeById(id);
        return R.success("车位信息删除成功");
    }

    /**
     * 根据条件查询车位数据
     * @param parking
     * @return
     */
    @GetMapping("/list")
    public R<List<Parking>> list(Parking parking){
        LambdaQueryWrapper<Parking> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(parking.getCategoryId() != null,Parking::getCategoryId,parking.getCategoryId());
        queryWrapper.eq(parking.getStatus() != null,Parking::getStatus,parking.getStatus());
        queryWrapper.orderByDesc(Parking::getUpdateTime);

        List<Parking> list = parkingService.list(queryWrapper);

        return R.success(list);
    }

    @PutMapping("/status")
    public R<String> updateHouseStatus(@RequestParam("ids") String ids, @RequestParam("status") Integer status) {
        try {
            List<Long> idList = Arrays.stream(ids.split(","))
                    .map(Long::parseLong)
                    .collect(Collectors.toList());
            if (ids.contains(",")) {
                // 如果ids包含逗号，表示是批量修改
                parkingService.updateBatchStatus(idList, status);
                return R.success("车位状态已经批量修改成功！");
            } else {
                // 否则，表示是单独修改
                parkingService.updateStatus(idList.get(0), status);
                return R.success("车位状态已经单独修改成功！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("操作失败：" + e.getMessage());
        }
    }
}
