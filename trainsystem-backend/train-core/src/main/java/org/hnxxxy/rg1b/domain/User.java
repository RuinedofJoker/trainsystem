package org.hnxxxy.rg1b.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hnxxxy.rg1b.common.annotation.Excel;
import org.hnxxxy.rg1b.common.core.domain.entity.SysUser;
import org.hnxxxy.rg1b.utils.BeanUtils;

/**
 * 用户对象 user 对应表sys_user
 * 所有用户的 role_id为100，role_name为用户 并且没有dept(部门)、post(职位)等字段(这些字段值为null)
 */
@Data
@NoArgsConstructor
public class User {

    /** 用户ID */
    private Long userId;
    /** 用户账号 */
    private String userName;
    /** 用户昵称 */
    private String nickName;
    /** 用户邮箱 */
    private String email;
    /** 手机号码 */
    private String phonenumber;
    /** 用户性别 */
    private String sex;
    /** 用户头像 */
    private String avatar;

    public User(SysUser sysUser) {
        BeanUtils.copyProperties(sysUser, this);
    }
}
