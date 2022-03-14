package com.yang.blog.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Description:
 * @Author: Guo.Yang
 * @Date: 2022/03/12/14:02
 */
@Data
@TableName("m_blog")
public class Blog implements Serializable {
    @TableId
    private Long id;
    private Long userId;

    @NotBlank(message = "标题不能为空！")
    private String title;

    @NotBlank(message = "摘要不能为空！")
    private String description;

    @NotBlank(message = "内容不能为空！")
    private String content;
    private LocalDateTime created;
    private Integer status;
}
