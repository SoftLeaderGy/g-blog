package com.yang.blog.service;

import com.yang.blog.common.lang.Result;
import com.yang.blog.entity.User;
import org.springframework.stereotype.Service;

/**
 * (MUser)表服务接口
 *
 * @author makejava
 * @since 2022-03-08 21:37:52
 */
public interface UserService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Result<User> queryById(Long id);
    Result<User> queryById(String id);


    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

    Result<User> getUserByUsername(String username);

    Result<User> getUserByEmail(String username);
}
