package org.hnxxxy.rg1b.service.concern;

import org.hnxxxy.rg1b.domain.ConcernList;
import org.hnxxxy.rg1b.domain.User;
import org.hnxxxy.rg1b.domain.vo.ConcernUserVo;

import java.util.List;

public interface IUserConcernService {

    /**
     * 获取当前用户的关注列表
     */
    List<ConcernUserVo> selectConcernList();

    /**
     * 添加关注
     */
    ConcernList insertConcern(Long userId, Long concernUserId);

    /**
     * 取消关注
     */
    boolean deleteConcern(Long userId, Long concernUserId);
}
