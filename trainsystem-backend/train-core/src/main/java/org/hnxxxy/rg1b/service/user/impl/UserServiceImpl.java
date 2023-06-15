package org.hnxxxy.rg1b.service.user.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.hnxxxy.rg1b.common.core.domain.entity.SysUser;
import org.hnxxxy.rg1b.domain.User;
import org.hnxxxy.rg1b.service.user.IUserService;
import org.hnxxxy.rg1b.system.mapper.SysUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    SysUserMapper sysUserMapper;


    @Override
    public User selectUserByUserId(Long userId) {

        User user = new User(sysUserMapper.selectUserById(userId));

        return user;
    }
}
