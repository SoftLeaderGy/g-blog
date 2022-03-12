package com.yang.blog.controller;

import cn.hutool.http.server.HttpServerResponse;
import com.yang.blog.common.dto.LoginDTO;
import com.yang.blog.common.exception.GblogException;
import com.yang.blog.common.lang.Result;
import com.yang.blog.entity.User;
import com.yang.blog.service.UserService;
import com.yang.blog.utils.JwtUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

/**
 * @Description:
 * @Author: Guo.Yang
 * @Date: 2022/03/11/22:31
 */
@RestController
public class AccountController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtils jwtUtils;

    /**
     * 登陆接口
     * @param loginDTO
     * @param response
     * @return
     */
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public Result login(@Validated @RequestBody LoginDTO loginDTO, HttpServletResponse response){
        Result<User> userResult = userService.getUserByUsername(loginDTO.getUsername());
        if(userResult.getData() == null){
            throw new GblogException("用户不存在！");
        }

        if(!userResult.getData().getPassword().equals(loginDTO.getPassword())){
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
