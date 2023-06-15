package org.hnxxxy.rg1b.web.controller.system;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.hnxxxy.rg1b.common.core.controller.BaseController;
import org.hnxxxy.rg1b.common.core.domain.AjaxResult;
import org.hnxxxy.rg1b.common.core.domain.model.RegisterBody;
import org.hnxxxy.rg1b.common.utils.StringUtils;
import org.hnxxxy.rg1b.framework.web.service.SysRegisterService;
import org.hnxxxy.rg1b.system.service.ISysConfigService;

/**
 * 注册验证
 *
 * @author ruoyi
 */
//@RestController
public class SysRegisterController extends BaseController
{
    @Autowired
    private SysRegisterService registerService;

    @Autowired
    private ISysConfigService configService;

    //@ApiOperation("注册...")
    //@PostMapping("/register")
    public AjaxResult register(@RequestBody RegisterBody user)
    {
        if (!("true".equals(configService.selectConfigByKey("sys.account.registerUser"))))
        {
            return error("当前系统没有开启注册功能！");
        }
        String msg = registerService.register(user);
        return StringUtils.isEmpty(msg) ? success() : error(msg);
    }
}
