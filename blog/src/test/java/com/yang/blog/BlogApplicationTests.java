package com.yang.blog;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.digest.MD5;
import org.apache.catalina.security.SecurityUtil;
import org.apache.shiro.SecurityUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

class BlogApplicationTests {

    @Test
    void contextLoads() {
        String s = SecureUtil.md5("123");
        System.out.println(s);
    }

}
