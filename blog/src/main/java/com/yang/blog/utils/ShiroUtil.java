package com.yang.blog.utils;

import com.alibaba.fastjson.JSONObject;
import com.yang.blog.shiro.AccountProfile;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.BeanUtils;

/**
 * @Description: Shiro工具类 获取登录的用户信息
 * @Author: Guo.Yang
 * @Date: 2022/03/12/15:29
 */
public class ShiroUtil {

    public static AccountProfile getProfile(){
        // 从redis反序列化，获取登录的用户信息
        Object principal = SecurityUtils.getSubject().getPrincipal();

        AccountProfile accountProfile1 = new AccountProfile();
        // 将反序列化的对象拷贝的新的 AccountProfile 对象中，这里不可以强转
        // 虽然是同一个类，但是同一个类被不同的类加载器加载后，在内存中就是不同的
        BeanUtils.copyProperties(principal,accountProfile1);
        // 返回用户信息
        return accountProfile1;
    }
}
