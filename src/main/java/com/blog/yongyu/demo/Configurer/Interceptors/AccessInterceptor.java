package com.blog.yongyu.demo.Configurer.Interceptors;

import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 解决跨域问题
 */
@Controller
@Component
public class AccessInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //设置：Access-Control-Allow-Origin头，处理session问题
        if (request.getMethod().equals("OPTIONS")) {
            response.setStatus(HttpStatus.OK.value());
        }
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials","true");
        response.setHeader("P3P","CP=CAO PSA OUR");
//        if (request.getHeader("Access-Control-Request-Method") != null && "OPTION".equals(request.getMethod())) {
        response.addHeader("Access-Control-Allow-Methods", "POST,GET,TRACE,OPTION,DELETE");
        response.addHeader("Access-Control-Allow-Headers", "Authentication,Origin, X-Requested-With, Content-Type, Accept,token");
//            response.addHeader("Access-Control-Max-Age", "120");
//        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {

    }
}