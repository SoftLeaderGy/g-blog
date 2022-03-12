package com.yang.blog.controller;

import com.yang.blog.common.lang.Result;
import com.yang.blog.entity.User;
import com.yang.blog.service.UserService;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * (MUser)表控制层
 *
 * @author makejava
 * @since 2022-03-08 21:37:48
 */
@RestController
@RequestMapping("/user")
public class UserController {
    /**
     * 服务对象
     */
    @Autowired
    private UserService userService;

    /**
     * 分页查询
     *
     * @param id 筛选条件
     * @param id      分页对象
     * @return 查询结果
     */
    @GetMapping("/{id}")
    @RequiresAuthentication // 表示 这个请求需要认证以后才可以访问
    public Object test(@PathVariable("id") Long id) {
        return userService.queryById(id);
    }


    @PostMapping("/save")
    // @RequiresAuthentication // 表示 这个请求需要认证以后才可以访问
    public Result save(@Validated @RequestBody User user) {
        return Result.succ(user);
    }

}

