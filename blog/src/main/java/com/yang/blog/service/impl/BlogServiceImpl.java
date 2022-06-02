package com.yang.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yang.blog.common.lang.Result;
import com.yang.blog.dao.BlogDao;
import com.yang.blog.entity.Blog;
import com.yang.blog.entity.User;
import com.yang.blog.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description:
 * @Author: Guo.Yang
 * @Date: 2022/03/12/14:20
 */
@Service("BlogServiceImpl")
public class BlogServiceImpl extends ServiceImpl<BlogDao, Blog> implements BlogService {

    @Autowired
    private BlogDao blogDao;
    @Override
    public Blog queryById(Long id) {
        return blogDao.selectById(id);
    }
    @Override
    public Blog queryById(String id) {
        return blogDao.selectById(id);
    }

    @Override
    public Result queryByPage(Long currPagae, Long pageSize) {

        Page<Blog> blogPage = new Page<>();
        blogPage.setCurrent(currPagae);
        blogPage.setSize(pageSize);
        QueryWrapper<Blog> blogQueryWrapper = new QueryWrapper<>();
        blogQueryWrapper.orderByDesc("created");
        IPage<Blog> blogIPage = blogDao.selectPage(blogPage, blogQueryWrapper);
        return Result.succ(blogIPage);
    }

    @Override
    public Blog insert(Blog mBlog) {
        return null;
    }

    @Override
    public Blog update(Blog mBlog) {
        return null;
    }

    @Override
    public boolean deleteById(Long id) {
        return false;
    }

    @Override
    public List<Blog> orderUserList(Page<QueryWrapper> page, User user) {
        return null;
    }

}
