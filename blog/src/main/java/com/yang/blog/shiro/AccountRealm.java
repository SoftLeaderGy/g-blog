package com.yang.blog.shiro;

import com.yang.blog.common.lang.Result;
import com.yang.blog.entity.User;
import com.yang.blog.service.UserService;
import com.yang.blog.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthenticatingRealm;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Description:自定义shiro的Relam
 * 重写 授权、认证两个方法
 * @Author: Guo.Yang
 * @Date: 2022/03/08/23:37
 */
@Component
@Slf4j
public class AccountRealm extends AuthorizingRealm {

    @Autowired
    UserService userService;

    @Autowired
    JwtUtils jwtUtils;
    /**
     * 授权
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        log.info("开始授权-----》");
        return null;
    }

    /**
     * 认证
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        JwtToken jwtToken = (JwtToken) token;

        // 解析 token 返回的claim claim就是存放在token中的用户信息 参数为token
        Claims claim = jwtUtils.getClaimByToken((String) jwtToken.getPrincipal());

        // 获取的就是 用户id
        String userId = claim.getSubject();

        Result<User> userResult = userService.queryById(Long.parseLong(userId));

        // 如果查询不到用户，就抛出账户不存在的异常
        if (userResult.getData() == null){
            throw new UnknownAccountException("账户不存在");
        }
        if (userResult.getData().getStatus() == -1) {
            throw new LockedAccountException("账户已被锁定");
        }

        // 创建登录后返回的用户对象信息
        AccountProfile accountProfile = new AccountProfile();
        // 将用户的部分信息复制给accountProfile
        BeanUtils.copyProperties(userResult.getData(),accountProfile);

        log.info("开始认证-----》");
        return null;
    }
}
