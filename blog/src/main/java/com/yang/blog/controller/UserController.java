package com.yang.blog.controller;

import com.yang.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Object test(@PathVariable("id") Long id) {
        return userService.queryById(id);
    }

}

