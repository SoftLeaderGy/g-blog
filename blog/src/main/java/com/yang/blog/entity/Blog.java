package com.yang.blog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
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
    @TableId(type = IdType.UUID)
    private String id;
    private String userId;

    @NotBlank(message = "标题不能为空！")
    private String title;

    @NotBlank(message = "摘要不能为空！")
    private String description;

    @NotBlank(message = "内容不能为空！")
    private String content;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime created;
    private Integer status;
}
