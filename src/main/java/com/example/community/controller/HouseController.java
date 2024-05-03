package com.example.community.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.community.common.R;
import com.example.community.dto.HouseDto;
import com.example.community.entity.Category;
import com.example.community.entity.House;
import com.example.community.service.CategoryService;
import com.example.community.service.HouseService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 房屋管理
 */
@RestController
@RequestMapping("/house")
@Slf4j
public class HouseController {
    @Autowired
    private HouseService houseService;

    @Autowired
    private CategoryService categoryService;

    /**
     * 新增房屋
     * @param houseDto
     * @return
     */
    @PostMapping
    public R<String> save(@RequestBody HouseDto houseDto){
        log.info(houseDto.toString());

        houseService.save(houseDto);

        return R.success("新增房屋成功");
    }

    /**
     * 房屋信息分页查询
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public R<Page> page(int page,int pageSize,String name){

        //构造分页构造器对象
        Page<House> pageInfo = new Page<>(page,pageSize);
        Page<HouseDto> houseDtoPage = new Page<>();

        //条件构造器
        LambdaQueryWrapper<House> queryWrapper = new LambdaQueryWrapper<>();
        //添加过滤条件
        queryWrapper.like(name != null,House::getName,name);
        //添加排序条件
        queryWrapper.orderByDesc(House::getUpdateTime);

        //执行分页查询
        houseService.page(pageInfo,queryWrapper);

        //对象拷贝
        BeanUtils.copyProperties(pageInfo,houseDtoPage,"records");

        List<House> records = pageInfo.getRecords();

        List<HouseDto> list = records.stream().map((item) -> {
            HouseDto houseDto = new HouseDto();

            BeanUtils.copyProperties(item,houseDto);

            Long categoryId = item.getCategoryId();//小区id
            //根据id查询分类对象
            Category category = categoryService.getById(categoryId);

            if(category != null){
                String categoryName = category.getName();
                houseDto.setCategoryName(categoryName);
            }
            return houseDto;
        }).collect(Collectors.toList());

        houseDtoPage.setRecords(list);

        return R.success(houseDtoPage);
    }

    /**
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R<HouseDto> get(@PathVariable Long id){

        House house = houseService.getById(id);

        HouseDto houseDto = new HouseDto();
        BeanUtils.copyProperties(house,houseDto);
        return R.success(houseDto);
    }

    /**
     * 修改房屋
     * @param houseDto
     * @return
     */
    @PutMapping
    public R<String> update(@RequestBody HouseDto houseDto){
        log.info(houseDto.toString());

        houseService.updateById(houseDto);

        return R.success("修改房屋成功");
    }



    /**
     * 根据条件查询对应的房屋数据
     * @param house
     * @return
     */
    @GetMapping("/list")
    public R<List<HouseDto>> list(House house) {
        log.info("house:{}", house);
        //条件构造器
        LambdaQueryWrapper<House> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotEmpty(house.getName()), House::getName, house.getName());
        queryWrapper.eq(null != house.getCategoryId(), House::getCategoryId, house.getCategoryId());
        queryWrapper.eq(House::getStatus,1);
        queryWrapper.orderByDesc(House::getUpdateTime);

        List<House> houses = houseService.list(queryWrapper);

        List<HouseDto> houseDtos = houses.stream().map(item -> {
            HouseDto houseDto = new HouseDto();
            BeanUtils.copyProperties(item, houseDto);
            Category category = categoryService.getById(item.getCategoryId());
            if (category != null) {
                houseDto.setCategoryName(category.getName());
            }

            return houseDto;
        }).collect(Collectors.toList());

        return R.success(houseDtos);
    }

    @PutMapping("/status")
    public R<String> updateHouseStatus(@RequestParam("ids") String ids, @RequestParam("status") Integer status) {
        try {
            List<Long> idList = Arrays.stream(ids.split(","))
                    .map(Long::parseLong)
                    .collect(Collectors.toList());
            if (ids.contains(",")) {
                // 如果ids包含逗号，表示是批量修改
                houseService.updateBatchStatus(idList, status);
                return R.success("房屋状态已经批量修改成功！");
            } else {
                // 否则，表示是单独修改
                houseService.updateStatus(idList.get(0), status);
                return R.success("房屋状态已经单独修改成功！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("操作失败：" + e.getMessage());
        }
    }

    @DeleteMapping
    public R<String> delete(Long id){
        log.info("删除房屋，id为：{}",id);
        houseService.removeById(id);
        return R.success("分类信息删除成功");
    }
}
