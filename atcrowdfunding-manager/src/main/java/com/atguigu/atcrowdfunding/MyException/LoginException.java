package com.atguigu.atcrowdfunding.MyException;

/**
 * 自定义异常，处理登录用户名和密码为空抛出异常
 * @Author: ouyong
 * @Date: 2020/07/25 19:43
 */
public class LoginException extends RuntimeException {
    public LoginException(){}

    public LoginException(String s){
        super(s);
    }
}
