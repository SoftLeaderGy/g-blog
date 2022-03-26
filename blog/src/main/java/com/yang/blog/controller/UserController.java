package com.yang.blog.controller;

import com.yang.blog.common.lang.Result;
import com.yang.blog.entity.User;
import com.yang.blog.service.UserService;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

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


    @PostMapping("/upload") // 等价于 @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public Result uplaod(HttpServletRequest req, @RequestParam("file") MultipartFile file, Model m) {//1. 接受上传的文件  @RequestParam("file") MultipartFile file
        try {
            //2.根据时间戳创建新的文件名，这样即便是第二次上传相同名称的文件，也不会把第一次的文件覆盖了
            String fileName = System.currentTimeMillis() + file.getOriginalFilename();
            //3.通过req.getServletContext().getRealPath("") 获取当前项目的真实路径，然后拼接前面的文件名
            String destFileName = req.getServletContext().getRealPath("") + "uploaded" + File.separator + fileName;
            //4.第一次运行的时候，这个文件所在的目录往往是不存在的，这里需要创建一下目录（创建到了webapp下uploaded文件夹下）
            File destFile = new File(destFileName);
            destFile.getParentFile().mkdirs();
            //5.把浏览器上传的文件复制到希望的位置
            file.transferTo(destFile);
            //6.把文件名放在model里，以便后续显示用
            m.addAttribute("fileName", fileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return Result.fail(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            return Result.fail(e.getMessage());
        }
        return Result.succ("操作成功");
    }

}

