package org.hnxxxy.rg1b.controller.train;

import org.hnxxxy.rg1b.common.ResultVo;
import org.hnxxxy.rg1b.common.core.domain.entity.SysUser;
import org.hnxxxy.rg1b.common.utils.SecurityUtils;
import org.hnxxxy.rg1b.domain.vo.OptimalRouteVo;
import org.hnxxxy.rg1b.domain.vo.TrainIntelVo;
import org.hnxxxy.rg1b.exception.IllegalTrainIntelException;
import org.hnxxxy.rg1b.framework.web.service.SysPermissionService;
import org.hnxxxy.rg1b.service.train.ITrainIntelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

/**
 * 推荐路径算法的控制层
 */
@RestController
@RequestMapping("/train/intel")
public class TrainIntelController {

    @Autowired
    private ITrainIntelService trainIntelService;

    @PostMapping("/optimal")
    @PreAuthorize("@ss.hasRole('vip')")
    public ResultVo optimal(@RequestBody TrainIntelVo trainIntelVo){

        SysUser user = SecurityUtils.getLoginUser().getUser();
        Long userId = user.getUserId();

        List<OptimalRouteVo> optimalRouteVo = null;
        try {
            optimalRouteVo = trainIntelService.generateOptimalRoute(trainIntelVo, userId);
        } catch (IllegalTrainIntelException e) {
            return ResultVo.error("请按顺序输入您的站点");
        }
        return ResultVo.success(optimalRouteVo);
    }
}
