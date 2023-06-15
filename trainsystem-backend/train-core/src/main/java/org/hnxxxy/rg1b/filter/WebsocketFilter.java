package org.hnxxxy.rg1b.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.hnxxxy.rg1b.common.constant.CacheConstants;
import org.hnxxxy.rg1b.common.constant.Constants;
import org.hnxxxy.rg1b.common.core.domain.model.LoginUser;
import org.hnxxxy.rg1b.common.core.redis.RedisCache;
import org.hnxxxy.rg1b.common.utils.SecurityUtils;
import org.hnxxxy.rg1b.common.utils.StringUtils;
import org.hnxxxy.rg1b.framework.web.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@WebFilter(filterName = "WebsocketFilter", urlPatterns = "/websocket/*")
public class WebsocketFilter implements Filter {

    @Value("${token.secret}")
    private String secret;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String requestURI = request.getRequestURI();
        if (requestURI.indexOf("websocket") == -1){
            filterChain.doFilter(request, response);
        }

        String token = request.getHeader("Sec-WebSocket-Protocol");

        response.setHeader("Sec-WebSocket-Protocol",token);

        if (StringUtils.isNotEmpty(token)) {
            try {
                Claims claims = parseToken(token);
                // 解析对应的权限以及用户信息
                String uuid = (String) claims.get(Constants.LOGIN_USER_KEY);
                String userKey = getTokenKey(uuid);
                request.getSession().setAttribute("userKey", userKey);
                filterChain.doFilter(request, response);
            }
            catch (Exception e) {}
        }
    }

    private Claims parseToken(String token)
    {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

    private String getTokenKey(String uuid)
    {
        return CacheConstants.LOGIN_TOKEN_KEY + uuid;
    }
}
