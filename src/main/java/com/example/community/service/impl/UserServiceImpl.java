package com.example.community.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.community.common.CustomException;
import com.example.community.entity.User;
import com.example.community.mapper.UserMapper;
import com.example.community.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Override
    public void remove(Long id) {
        if(id==1)
        {
            throw new CustomException("主房东，不能删除");
        }
        super.removeById(id);
    }

    @Override
    public void update(Long id){
        if(id==1){
            throw new CustomException(("主房东，状态不能修改"));
        }
    }
}
