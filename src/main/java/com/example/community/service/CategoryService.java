package com.example.community.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.community.entity.Category;

public interface CategoryService extends IService<Category> {

    public void remove(Long id);

}
