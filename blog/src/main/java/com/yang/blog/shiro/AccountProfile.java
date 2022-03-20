package com.yang.blog.shiro;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description:  登录后返回的信息
 * @Author: Guo.Yang
 * @Date: 2022/03/10/23:05
 */
@Data
public class AccountProfile implements Serializable {
    private String id;

    private String username;

    private String avatar;

    private String email;

    private Date created;
}
