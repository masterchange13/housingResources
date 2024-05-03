package com.example.community.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.community.entity.User;

public interface UserService extends IService<User> {
    public void remove(Long id);
    public void update(Long id);
}
