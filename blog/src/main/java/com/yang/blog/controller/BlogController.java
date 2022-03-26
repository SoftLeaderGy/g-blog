package com.yang.blog.controller;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yang.blog.common.lang.Result;
import com.yang.blog.entity.Blog;
import com.yang.blog.entity.BlogDTO;
import com.yang.blog.entity.User;
import com.yang.blog.service.BlogService;
import com.yang.blog.service.UserService;
import com.yang.blog.utils.ShiroUtil;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Description: 博客接口
 * @Author: Guo.Yang
 * @Date: 2022/03/12/13:55
 */
@RestController
public class BlogController {

    @Autowired
    private BlogService blogService;
    @Autowired
    private UserService userService;
    /**
     * 分页查询博客列表
     * @param currentpage
     * @return
     */
    @GetMapping("/blog")
    public Result list(@RequestParam(defaultValue = "1") Integer currentpage){

        Result result = blogService.queryByPage(Long.parseLong(currentpage + ""), 5L);
        IPage<Blog> data = (IPage<Blog>) result.getData();
        List<Blog> records = data.getRecords();
        return Result.succ(data);
    }

    @GetMapping("/blogDetail/{id}")
    public Result blogDetail(@PathVariable(name = "id")  String id){

        Blog blog = blogService.queryById(id);
        if(blog == null){
            return Result.fail("博客已被删除!");
        }
        Result<User> userResult = userService.queryById(blog.getUserId());
        BlogDTO blogDTO = new BlogDTO();
        BeanUtils.copyProperties(blog,blogDTO);
        blogDTO.setAuthor(userResult.getData().getUsername());
        return Result.succ(blogDTO);
    }

//    @GetMapping("/blogDetail/{id}")
//    public Result blogDetail(@PathVariable(name = "id")  Long id){
//
//        Blog blog = blogService.queryById(id);
//        if(blog == null){
//            return Result.fail("博客已被删除!");
//        }
//        return Result.succ(blog);
//    }

    @RequiresAuthentication
    @PostMapping("/blog/edit")
    public Result edit(@Validated @RequestBody Blog blog){

        Blog temp = null;
        if(blog.getId() != null && !("".equals(blog.getId()))){
          // 编辑
            temp = blogService.queryById(blog.getId()+ "");
            // 只能编辑自己的文章
            // 断言的形式判断
            Assert.isTrue(temp.getUserId().equals(ShiroUtil.getProfile().getId()),"没有权限编辑文章");
        } else {
          // 新增
            temp = new Blog();
            temp.setUserId(ShiroUtil.getProfile().getId());
            temp.setCreated(LocalDateTime.now());
            temp.setStatus(0);
        }
        BeanUtils.copyProperties(blog,temp,"id","userId","created","startus");
        blogService.saveOrUpdate(temp);
        return Result.succ("操作成功");
    }

}
