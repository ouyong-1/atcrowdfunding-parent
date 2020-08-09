/*
package com.atguigu.atcrowdfunding.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

*/
/**
 * @Author: ouyong
 * @Date: 2020/08/01 10:48
 *//*

*/
/*@Configuration
@EnableWebSecurity// 开启安全框架
@EnableGlobalMethodSecurity(prePostEnabled = true)*//*

public class atcrowdfundingConfig extends WebSecurityConfigurerAdapter {

    // 认证
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // super.configure(auth);
    }

    // 授权
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // super.configure(http);
        // 1、授权静态资源和登录页可以被访问
        http.authorizeRequests().antMatchers("/static/**","/index.jsp").permitAll();
    }
}
*/
