package com.yang.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yang.blog.common.lang.Result;
import com.yang.blog.entity.User;
import com.yang.blog.dao.UserDao;
import com.yang.blog.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * (MUser)表服务实现类
 *
 * @author makejava
 * @since 2022-03-08 21:37:53
 */
@Service("UserService")
public class UserServiceImpl implements UserService {
    @Resource
    private UserDao mUserDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public Result<User> queryById(Long id) {
        User user = mUserDao.queryById(id);
        return Result.succ(user);
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.mUserDao.deleteById(id) > 0;
    }

    /**
     * 根据用户名获取用户信息
     * @param username
     * @return
     */
    @Override
    public Result<User> getUserByUsername(String username) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("username",username);
        User user = mUserDao.selectOne(queryWrapper);
        return Result.succ(user);
    }

}
