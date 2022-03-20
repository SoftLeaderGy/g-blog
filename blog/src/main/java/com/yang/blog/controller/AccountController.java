package com.yang.blog.controller;
import java.util.Date;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.http.server.HttpServerResponse;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yang.blog.common.dto.LoginDTO;
import com.yang.blog.common.exception.GblogException;
import com.yang.blog.common.lang.Result;
import com.yang.blog.dao.UserDao;
import com.yang.blog.entity.User;
import com.yang.blog.service.UserService;
import com.yang.blog.utils.JwtUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.UUID;

/**
 * @Description:
 * @Author: Guo.Yang
 * @Date: 2022/03/11/22:31
 */
@RestController
public class AccountController {

    @Autowired
    private UserService userService;

    @Resource
    private UserDao mUserDao;

    @Autowired
    private JwtUtils jwtUtils;

    @Value("${user.avatar}")
    private String avatar;
    /**
     * 登陆接口
     * @param loginDTO
     * @param response
     * @return
     */
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public Result login(@Validated @RequestBody LoginDTO loginDTO, HttpServletResponse response){
        Result<User> userResult = userService.getUserByEmail(loginDTO.getEmail());
        if(userResult.getData() == null){
            throw new GblogException("用户不存在！");
        }

        if(!userResult.getData().getPassword().equals(SecureUtil.md5(loginDTO.getPassword()))){
            return Result.fail("密码不正确");
        }

        // 账号密码正确
        String jwt = jwtUtils.generateToken(userResult.getData().getId());
        response.setHeader("accessToken", jwt);
        response.setHeader("Access-Control-Expose-Headers", "accessToken");
        HashMap<Object, Object> userMap = new HashMap<>();
        userMap.put("id",userResult.getData().getId());
        userMap.put("username",userResult.getData().getUsername());
        userMap.put("avatar",userResult.getData().getAvatar());
        userMap.put("email", userResult.getData().getEmail());
        return Result.succ(userMap);
    }

    @PostMapping("/register")
    public Result register(@Validated @RequestBody LoginDTO loginDTO, HttpServletResponse response){
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("email",loginDTO.getEmail());
        if(mUserDao.selectCount(queryWrapper) != 0){
            throw new GblogException("该邮箱已被注册过！");
        }
        User user = new User();
        user.setId(UUID.randomUUID().toString().replace("-",""));
        user.setUsername(loginDTO.getUsername());
        user.setAvatar(avatar);
        user.setEmail(loginDTO.getEmail());
        user.setPassword(SecureUtil.md5(loginDTO.getPassword()));
        user.setStatus(0);
        user.setCreated(new Date());
        user.setLastLogin(new Date());
        mUserDao.insert(user);
        return Result.succ("注册成功");
    }
    /**
     * 退出登陆
     * @return
     */
    @RequiresAuthentication // 退出登陆需要认证权限的信息
    @PostMapping("/logout")
    public Result logout(){

        // 退出登陆直接交给Shiro
        SecurityUtils.getSubject().logout();
        return Result.succ(null);
    }
}
