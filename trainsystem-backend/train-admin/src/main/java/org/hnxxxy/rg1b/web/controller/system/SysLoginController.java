package org.hnxxxy.rg1b.web.controller.system;

import java.util.List;
import java.util.Set;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.hnxxxy.rg1b.common.constant.Constants;
import org.hnxxxy.rg1b.common.core.domain.AjaxResult;
import org.hnxxxy.rg1b.common.core.domain.entity.SysMenu;
import org.hnxxxy.rg1b.common.core.domain.entity.SysUser;
import org.hnxxxy.rg1b.common.core.domain.model.LoginBody;
import org.hnxxxy.rg1b.common.utils.SecurityUtils;
import org.hnxxxy.rg1b.framework.web.service.SysLoginService;
import org.hnxxxy.rg1b.framework.web.service.SysPermissionService;
import org.hnxxxy.rg1b.system.service.ISysMenuService;

/**
 * 登录验证
 *
 * @author ruoyi
 */
@Api("登录")
@RestController
public class SysLoginController
{
    @Autowired
    private SysLoginService loginService;

    @Autowired
    private ISysMenuService menuService;

    @Autowired
    private SysPermissionService permissionService;


    /**
     * 登录方法
     *
     * @param loginBody 登录信息
     * @return 结果
     */
    @ApiOperation("用户/管理员登录")
    @ApiImplicitParam(name = "login",value = "用户/管理员登录",dataType = "LoginBody",dataTypeClass = LoginBody.class,required = true)
    @PostMapping("/login")
    public AjaxResult login(@RequestBody LoginBody loginBody)
    {
        AjaxResult ajax = AjaxResult.success();
        // 生成令牌
        String token = loginService.login(loginBody.getUsername(), loginBody.getPassword(), loginBody.getCode(),
                loginBody.getUuid());
        ajax.put(Constants.TOKEN, token);
        return ajax;
    }

    /**
     * 获取用户信息
     *
     * @return 用户信息
     */
    @ApiOperation("获取用户信息")
    @GetMapping("getInfo")
    public AjaxResult getInfo()
    {
        SysUser user = SecurityUtils.getLoginUser().getUser();
        // 角色集合
        Set<String> roles = permissionService.getRolePermission(user);
        // 权限集合
        Set<String> permissions = permissionService.getMenuPermission(user);
        AjaxResult ajax = AjaxResult.success();
        ajax.put("user", user);
        ajax.put("roles", roles);
        ajax.put("permissions", permissions);
        return ajax;
    }

    /**
     * 获取路由信息
     *
     * @return 路由信息
     */
    @GetMapping("getRouters")
    public AjaxResult getRouters()
    {
        Long userId = SecurityUtils.getUserId();
        List<SysMenu> menus = menuService.selectMenuTreeByUserId(userId);
        return AjaxResult.success(menuService.buildMenus(menus));
    }
}
