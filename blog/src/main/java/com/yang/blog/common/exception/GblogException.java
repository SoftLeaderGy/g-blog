package com.yang.blog.common.exception;

import org.springframework.stereotype.Component;

/**
 * @Description:
 * @Author: Guo.Yang
 * @Date: 2022/03/11/21:43
 */
@Component
public class GblogException extends RuntimeException {
    public GblogException(String message) {
        super(message);
    }

    public GblogException() {
    }
}
