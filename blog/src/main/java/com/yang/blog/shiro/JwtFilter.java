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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

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
        String jwt = request.getHeader("Authorization");
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
        String jwt = request.getHeader("Authorization");
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

            return executeLogin(request,servletResponse);
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

        Result res = Result.fail(throwable.getMessage());

        String s = JSONObject.toJSONString(res);

        HttpServerResponse request1 = (HttpServerResponse) request;

        request1.getWriter().print(request1);

        return super.onLoginFailure(token, e, request, response);
    }
}
