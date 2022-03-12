package com.yang.blog.shiro;

import cn.hutool.http.server.HttpServerResponse;
import com.alibaba.fastjson.JSONObject;
import com.yang.blog.common.lang.Result;
import com.yang.blog.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExpiredCredentialsException;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Description: 创建jwtFilter bean
 * @Author: Guo.Yang
 * @Date: 2022/03/10/21:37
 */
@Component
public class JwtFilter extends AuthenticatingFilter {

    @Autowired
    JwtUtils jwtUtils;
    /**
     * 从hander里边获取出jwt，并将一些信息封装成token
     * @param servletRequest
     * @param servletResponse
     * @return
     * @throws Exception
     */
    @Override
    protected AuthenticationToken createToken(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {

        // 从hander里边获取出jwt，并将一些信息封装成token
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String jwt = request.getHeader("accessToken");
        if(StringUtils.isEmpty(jwt)){
            return null;
        }

        return new JwtToken(jwt);
    }

    /**
     * 拦截
     * 验证jwt
     * @param servletRequest
     * @param servletResponse
     * @return
     * @throws Exception
     */
    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        // 验证jwt
        // 从hander里边获取出jwt
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String jwt = request.getHeader("accessToken");
        // 如果没有token的话，可以访问一些路径
        // 类似于 "游客" 一样的权限
        if(StringUtils.isEmpty(jwt)){
            return true;
        } else {
            // 校验token
            Claims claim = jwtUtils.getClaimByToken(jwt);
            // 验证token 如果token为空 或者 过期 抛出异常
            if(claim == null || jwtUtils.isTokenExpired(claim.getExpiration())){
                throw new ExpiredCredentialsException("token已过期，请重新登录！");
            }
            // 执行登录

            return executeLogin(servletRequest,servletResponse);
        }
    }

    /**
     * 为了返回给前端统一的数据格式
     * 重写登录失败方法，防止抛出异常 返回的不是统一的数据格式
     * @param token
     * @param e
     * @param request
     * @param response
     * @return
     */
    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {

        // 获取抛出异常的原因，也就是登录失败的原因
        Throwable throwable = e.getCause() == null ? e : e.getCause();

        Result r = Result.fail(throwable.getMessage());

        String s = JSONObject.toJSONString(r);

        HttpServletResponse response1 = (HttpServletResponse) response;

        try {
            response1.getWriter().print(s);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        return false;
    }

    /**
     * 过滤器也需要解决跨域问题
     *
     * @param request
     * @param response
     * @param mappedValue
     * @return
     * @throws Exception
     */
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = WebUtils.toHttp(request);
        HttpServletResponse httpServletResponse = WebUtils.toHttp(response);
        httpServletResponse.setHeader("Access-control-Allow-Origin", httpServletRequest.getHeader("Origin"));
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", httpServletRequest.getHeader("Access-Control-Request-Headers"));
        // 跨域时会首先发送一个OPTIONS请求，这里我们给OPTIONS请求直接返回正常状态
        if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
            httpServletResponse.setStatus(org.springframework.http.HttpStatus.OK.value());
            return false;
        }
        return super.preHandle(request, response);
    }
}
