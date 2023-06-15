package org.hnxxxy.rg1b.domain;

import lombok.Data;

import java.util.Date;

/**
 * 最优路径算法得到的优化路径
 */
@Data
public class OptimalRoute {

    private String routeId;//随机UUID，代表算法得到的一条路线
    private String description;//该路线的描述
    private Long userId;//用户id
    private Date getTime;//生成时间
    private Integer isAdopt;//是否被采用
}
