package org.hnxxxy.rg1b.web.controller.monitor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.hnxxxy.rg1b.common.annotation.Log;
import org.hnxxxy.rg1b.common.constant.CacheConstants;
import org.hnxxxy.rg1b.common.core.controller.BaseController;
import org.hnxxxy.rg1b.common.core.domain.AjaxResult;
import org.hnxxxy.rg1b.common.core.domain.model.LoginUser;
import org.hnxxxy.rg1b.common.core.page.TableDataInfo;
import org.hnxxxy.rg1b.common.core.redis.RedisCache;
import org.hnxxxy.rg1b.common.enums.BusinessType;
import org.hnxxxy.rg1b.common.utils.StringUtils;
import org.hnxxxy.rg1b.system.domain.SysUserOnline;
import org.hnxxxy.rg1b.system.service.ISysUserOnlineService;

/**
 * 在线用户监控
 *
 * @author ruoyi
 */
//@Api("在线用户监控")
@RestController
@RequestMapping("/monitor/online")
public class SysUserOnlineController extends BaseController
{
    @Autowired
    private ISysUserOnlineService userOnlineService;

    @Autowired
    private RedisCache redisCache;

    //@ApiOperation("监控用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ipaddr",value = "用户ip地址",dataType = "String",dataTypeClass = String.class),
            @ApiImplicitParam(name = "userName",value = "用户名",dataType = "String",dataTypeClass = String.class)
    })
    @PreAuthorize("@ss.hasPermi('monitor:online:list')")
    @GetMapping("/list")
    public TableDataInfo list(String ipaddr, String userName)
    {
        Collection<String> keys = redisCache.keys(CacheConstants.LOGIN_TOKEN_KEY + "*");
        List<SysUserOnline> userOnlineList = new ArrayList<SysUserOnline>();
        for (String key : keys)
        {
            LoginUser user = redisCache.getCacheObject(key);
            if (StringUtils.isNotEmpty(ipaddr) && StringUtils.isNotEmpty(userName))
            {
                userOnlineList.add(userOnlineService.selectOnlineByInfo(ipaddr, userName, user));
            }
            else if (StringUtils.isNotEmpty(ipaddr))
            {
                userOnlineList.add(userOnlineService.selectOnlineByIpaddr(ipaddr, user));
            }
            else if (StringUtils.isNotEmpty(userName) && StringUtils.isNotNull(user.getUser()))
            {
                userOnlineList.add(userOnlineService.selectOnlineByUserName(userName, user));
            }
            else
            {
                userOnlineList.add(userOnlineService.loginUserToUserOnline(user));
            }
        }
        Collections.reverse(userOnlineList);
        userOnlineList.removeAll(Collections.singleton(null));
        return getDataTable(userOnlineList);
    }

    /**
     * 强退用户
     */
    //@ApiOperation("强退用户")
    @ApiImplicitParam(name = "tokenId",value = "被删除的用户的token",dataType = "String",dataTypeClass = String.class,paramType = "path")
    @PreAuthorize("@ss.hasPermi('monitor:online:forceLogout')")
    @Log(title = "在线用户", businessType = BusinessType.FORCE)
    @DeleteMapping("/{tokenId}")
    public AjaxResult forceLogout(@PathVariable String tokenId)
    {
        redisCache.deleteObject(CacheConstants.LOGIN_TOKEN_KEY + tokenId);
        return success();
    }
}
