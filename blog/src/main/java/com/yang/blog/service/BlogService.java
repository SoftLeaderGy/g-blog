package com.yang.blog.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yang.blog.common.lang.Result;
import com.yang.blog.entity.Blog;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @Author: Guo.Yang
 * @Date: 2022/03/12/14:16
 */
@Service
public interface BlogService extends IService<Blog> {
    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Blog queryById(Long id);

    /**
     * 分页查询
     *
     * @param mBlog 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    Result queryByPage(Long currPagae, Long pageSize);

    /**
     * 新增数据
     *
     * @param mBlog 实例对象
     * @return 实例对象
     */
    Blog insert(Blog mBlog);

    /**
     * 修改数据
     *
     * @param mBlog 实例对象
     * @return 实例对象
     */
    Blog update(Blog mBlog);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);
}
