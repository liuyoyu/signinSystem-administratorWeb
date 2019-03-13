package com.blog.yongyu.demo.Configurer;

import com.blog.yongyu.demo.Configurer.Interceptors.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyConfigurer implements WebMvcConfigurer {

    @Autowired
    LoginInterceptor loginInterceptor;


    //用来注册拦截器，写好的拦截器在这儿添加注册才能生效
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(loginInterceptor).addPathPatterns("/**");
    }

    //用来配置静态资源:html，js，css等
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

    }
}
