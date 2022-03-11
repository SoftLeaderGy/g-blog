package com.yang.blog.common.lang;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description: 统一结果集
 * @Author: Guo.Yang
 * @Date: 2022/03/08/22:45
 */
@Data
public class Result<T> implements Serializable {
    private int code;
    private String msg;
    private T data;

    public static <T> Result<T> succ(int code, String msg, T data){
        Result<T> r = new Result<>();
        r.setCode(code);
        r.setMsg(msg);
        r.setData(data);
        return r;
    }

    public static <T>  Result<T> succ(T data){
        Result r = new Result<>();
        r.setCode(200);
        r.setMsg("操作成功");
        r.setData(data);
        return r;
    }

    public static <T> Result<T> fail(int code, String msg, T data){
        Result<T> r = new Result<>();
        r.setCode(code);
        r.setMsg(msg);
        r.setData(data);
        return r;
    }

    public static Result fail(String msg){
        Result r = new Result<>();
        r.setCode(400);
        r.setMsg(msg);
        r.setData(null);
        return r;
    }
}
