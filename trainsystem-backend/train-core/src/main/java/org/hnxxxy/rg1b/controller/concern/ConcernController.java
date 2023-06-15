package org.hnxxxy.rg1b.controller.concern;

import org.hnxxxy.rg1b.common.ResultVo;
import org.hnxxxy.rg1b.common.utils.SecurityUtils;
import org.hnxxxy.rg1b.domain.ConcernList;
import org.hnxxxy.rg1b.domain.User;
import org.hnxxxy.rg1b.domain.vo.ConcernUserVo;
import org.hnxxxy.rg1b.service.concern.IUserConcernService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/concern")
public class ConcernController {

    @Autowired
    private IUserConcernService userConcernService;

    /**
     * 获取当前用户的关注列表
     */
    @GetMapping("/list")
    public ResultVo getConcernList(){
        List<ConcernUserVo> users = userConcernService.selectConcernList();
        return ResultVo.success(users);
    }

    /**
     * 添加关注
     */
    @PostMapping("/{userId}")
    public ResultVo addConcern(@PathVariable Long userId){
        ConcernList concernList = userConcernService.insertConcern(SecurityUtils.getUserId(), userId);
        return ResultVo.success(concernList);
    }

    /**
     * 取消关注
     */
    @DeleteMapping("/{userId}")
    public ResultVo cancelConcern(@PathVariable Long userId){
        if (userConcernService.deleteConcern(SecurityUtils.getUserId(), userId)){
            return ResultVo.success("删除成功");
        }
        return ResultVo.error("删除失败");
    }
}
