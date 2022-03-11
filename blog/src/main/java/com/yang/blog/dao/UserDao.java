package com.yang.blog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yang.blog.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * (MUser)表数据库访问层
 *
 * @author makejava
 * @since 2022-03-08 21:37:50
 */
@Component
@Mapper
public interface UserDao extends BaseMapper<User> {
    User queryById(Long id);
}

