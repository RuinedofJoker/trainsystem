package org.hnxxxy.rg1b.service.train;

import org.hnxxxy.rg1b.domain.vo.OptimalRouteVo;
import org.hnxxxy.rg1b.domain.vo.TrainIntelVo;
import org.hnxxxy.rg1b.exception.IllegalTrainIntelException;

import java.util.List;

public interface ITrainIntelService {

    /**
     * 根据前端传入的用户起始站、终点站、途中必须经过的城市（可选）得到一条或多条最优路径
     * @param trainIntelVo
     * @return
     */
    List<OptimalRouteVo> generateOptimalRoute(TrainIntelVo trainIntelVo, Long userId) throws IllegalTrainIntelException;
}
