package com.yang.blog.common.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @Description:
 * @Author: Guo.Yang
 * @Date: 2022/03/11/22:33
 */
@Data
public class LoginDTO implements Serializable {

    @NotBlank(message = "用户名不可为空！")
    private String username;

    @NotBlank(message = "密码不可为空！")
    private String password;
}
