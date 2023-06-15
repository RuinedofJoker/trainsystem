package org.hnxxxy.rg1b.domain.vo;

import lombok.Data;
import org.hnxxxy.rg1b.domain.OptimalRoute;
import org.hnxxxy.rg1b.domain.RoutePath;

import java.util.List;

/**
 * 最优路径算法得到的某条优化路径
 */
@Data
public class OptimalRouteVo extends OptimalRoute {

    private List<RoutePath> routePaths;
}
