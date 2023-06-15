package org.hnxxxy.rg1b.controller.user;

import org.hnxxxy.rg1b.common.ResultVo;
import org.hnxxxy.rg1b.common.core.domain.entity.SysRole;
import org.hnxxxy.rg1b.common.core.domain.entity.SysUser;
import org.hnxxxy.rg1b.common.utils.SecurityUtils;
import org.hnxxxy.rg1b.common.utils.StringUtils;
import org.hnxxxy.rg1b.domain.User;
import org.hnxxxy.rg1b.service.user.IUserService;
import org.hnxxxy.rg1b.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService userService;
    @Autowired
    private ISysUserService sysUserService;

    @PostMapping("/register")
    public ResultVo userRegister(@RequestBody SysUser user){
        //用户注册(用户的roleId固定为100)
        user.setRoleId(100L);
        user.setRoleIds(new Long[]{100L});
        if (!sysUserService.checkUserNameUnique(user))
        {
            return ResultVo.error("新增用户'" + user.getUserName() + "'失败，登录账号已存在");
        }
        else if (StringUtils.isNotEmpty(user.getPhonenumber()) && !sysUserService.checkPhoneUnique(user))
        {
            return ResultVo.error("新增用户'" + user.getUserName() + "'失败，手机号码已存在");
        }
        else if (StringUtils.isNotEmpty(user.getEmail()) && !sysUserService.checkEmailUnique(user))
        {
            return ResultVo.error("新增用户'" + user.getUserName() + "'失败，邮箱账号已存在");
        }
        user.setCreateBy(user.getUserName());
        user.setPassword(SecurityUtils.encryptPassword(user.getPassword()));
        sysUserService.insertUser(user);

        return ResultVo.success("注册成功");
    }

    @GetMapping("/getRoles")
    public ResultVo getRoles(){
        List<SysRole> roles = SecurityUtils.getLoginUser().getUser().getRoles();
        return ResultVo.success(roles);
    }

    @GetMapping("/info/{userId}")
    public ResultVo getInfo(@PathVariable Long userId){
        User user = userService.selectUserByUserId(userId);
        return ResultVo.success(user);
    }
}
