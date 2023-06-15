package org.hnxxxy.rg1b.service.concern.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.hnxxxy.rg1b.common.core.domain.entity.SysUser;
import org.hnxxxy.rg1b.common.utils.SecurityUtils;
import org.hnxxxy.rg1b.domain.ConcernList;
import org.hnxxxy.rg1b.domain.User;
import org.hnxxxy.rg1b.domain.vo.ConcernUserVo;
import org.hnxxxy.rg1b.mapper.ConcernListMapper;
import org.hnxxxy.rg1b.mapper.PrivateMessageMapper;
import org.hnxxxy.rg1b.service.concern.IUserConcernService;
import org.hnxxxy.rg1b.system.mapper.SysUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class UserConcernServiceImpl implements IUserConcernService {

    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private ConcernListMapper concernListMapper;

    @Override
    public List<ConcernUserVo> selectConcernList() {
        Long userId = SecurityUtils.getLoginUser().getUser().getUserId();

        LambdaQueryWrapper<ConcernList> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ConcernList::getUserId, userId);
        List<ConcernList> concernLists = concernListMapper.selectList(queryWrapper);

        List<ConcernUserVo> users = new ArrayList<>();

        concernLists.forEach((concernList) -> {
            Long concernUserId = concernList.getConcernUserId();
            ConcernUserVo user = new ConcernUserVo(sysUserMapper.selectUserById(concernUserId));
            user.setConcernId(concernList.getConcernId());
            user.setDate(concernList.getDate());

            users.add(user);
        });

        return users;
    }

    @Override
    public ConcernList insertConcern(Long userId, Long concernUserId) {
        //判断被关注用户是否存在
        SysUser sysUser = sysUserMapper.selectUserById(concernUserId);
        if (!ObjectUtils.isEmpty(sysUser)){
            ConcernList concernList = new ConcernList();
            concernList.setConcernId(UUID.randomUUID().toString());
            concernList.setUserId(userId);
            concernList.setConcernUserId(concernUserId);
            concernList.setDate(new Date());
            //判断是否重复关注
            LambdaQueryWrapper<ConcernList> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper
                    .eq(ConcernList::getUserId, userId)
                    .eq(ConcernList::getConcernUserId, concernUserId);
            Integer count = concernListMapper.selectCount(queryWrapper);
            if (count == 0){
                int insert = concernListMapper.insert(concernList);
                if (insert != 0){
                    return concernList;
                }
            }
        }

        return null;
    }

    @Override
    public boolean deleteConcern(Long userId, Long concernUserId) {

        LambdaQueryWrapper<ConcernList> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(ConcernList::getUserId, userId)
                .eq(ConcernList::getConcernUserId, concernUserId);
        int delete = concernListMapper.delete(queryWrapper);

        return delete != 0 ? true : false;
    }
}
