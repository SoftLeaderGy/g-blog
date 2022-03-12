package com.yang.blog.common.exception;

import com.yang.blog.common.lang.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.ShiroException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Description: 全局异常捕获
 * @Author: Guo.Yang
 * @Date: 2022/03/11/20:35
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {


    /**
     * 捕获系统中所有的运行时异常
     * @param e
     * @return
     */
    @ResponseStatus(value = HttpStatus.BAD_REQUEST) // 返回给前端请求的状态码
    @ExceptionHandler(value = RuntimeException.class) // 捕获RuntimeException
    public Result handler(RuntimeException e){
        log.error("运行时异常--------> " + e);
        return Result.fail(e.getMessage());
    }


    /**
     * 捕获认证授权抛出的ShiroException异常
     * @param e
     * @return
     */
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED) // 返回给前端没有权限的请求的状态码
    @ExceptionHandler(value = ShiroException.class) // 捕获ShiroException
    public Result shiroHandler(ShiroException e){
        log.error("ShiroException--------> " + e);
        return Result.fail(401,e.getMessage(),null);
    }


    /**
     * 捕获实体检验的MethodArgumentNotValidException异常
     * @param e
     * @return
     */
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED) // 返回给前端没有权限的请求的状态码
    @ExceptionHandler(value = MethodArgumentNotValidException.class) // 捕获ShiroException
    public Result handler(MethodArgumentNotValidException e){
        log.error("ShiroException--------> " + e);

        // 获取抛出异常的细节信息
        BindingResult bindingResult = e.getBindingResult();
        ObjectError objectError = bindingResult.getAllErrors().stream().findFirst().get();
        String defaultMessage = objectError.getDefaultMessage();

        return Result.fail(401,defaultMessage,null);
    }

    @ResponseStatus(value = HttpStatus.UNAUTHORIZED) // 返回给前端没有权限的请求的状态码
    @ExceptionHandler(value = GblogException.class) // 捕获ShiroException
    public Result handler(GblogException e){
        log.error("GblogException--------> " + e);
        // 获取抛出异常的细节信息
        return Result.fail(400,e.getMessage(),null);
    }

    @ResponseStatus(value = HttpStatus.UNAUTHORIZED) // 返回给前端没有权限的请求的状态码
    @ExceptionHandler(value = Exception.class) // 捕获ShiroException
    public Result handler(Exception e){
        log.error("GblogException--------> " + e);
        return Result.fail(400,e.getMessage(),null);
    }
}
