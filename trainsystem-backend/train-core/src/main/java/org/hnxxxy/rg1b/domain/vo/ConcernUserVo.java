package org.hnxxxy.rg1b.domain.vo;

import lombok.Data;
import org.hnxxxy.rg1b.common.core.domain.entity.SysUser;
import org.hnxxxy.rg1b.domain.User;
import org.hnxxxy.rg1b.utils.BeanUtils;

import java.util.Date;

@Data
public class ConcernUserVo extends User {

    private String concernId;
    private Date date;

    public ConcernUserVo(SysUser sysUser) {
        BeanUtils.copyProperties(sysUser, this);
    }
}
